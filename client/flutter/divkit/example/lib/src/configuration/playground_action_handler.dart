import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:divkit/divkit.dart';
import 'package:example/src/pages/show.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../state.dart';

const _demoActivity = 'demo_activity';
const _schemeDivAction = 'div-action';
const _activityShowResult = 'show_result';
const _activityPaste = 'paste';
const _activityOpenQr = 'open_qr';
const _activityOpenFile = 'open_file';
const _paramAction = 'action';

class PlaygroundActionHandler implements DivActionHandler {
  final GlobalKey<NavigatorState> _navigator;

  NavigatorState get _navigationManager =>
      Navigator.of(_navigator.currentContext!);

  PlaygroundActionHandler({
    required GlobalKey<NavigatorState> navigator,
  }) : _navigator = navigator;

  @override
  bool canHandle(DivContext context, DivActionModel action) {
    final uri = action.url;
    if (uri != null) {
      if (uri.scheme == _schemeDivAction &&
          uri.host == _demoActivity &&
          [
            _activityShowResult,
            _activityPaste,
            _activityOpenQr,
            _activityOpenFile,
          ].contains(uri.queryParameters[_paramAction])) {
        return true;
      }
    }
    return false;
  }

  @override
  FutureOr<bool> handleAction(
    DivContext context,
    DivActionModel action,
  ) async {
    final uri = action.url;
    if (uri == null) {
      return false;
    }
    return await handleUrlAction(context, uri);
  }

  Future<String?> fetch(Uri uri) async {
    try {
      final client = HttpClient();

      final request = await client.getUrl(uri);
      final response = await request.close();

      if (response.statusCode == HttpStatus.ok) {
        return response.transform(utf8.decoder).join();
      } else {
        showSnackBar('HTTP request failed, status: ${response.statusCode}');
      }
    } catch (e) {
      showSnackBar('Error fetching data: $e');
    }
    return null;
  }

  Future<bool> handleUrlAction(DivContext context, Uri uri) async {
    if (uri.scheme != _schemeDivAction || uri.host != _demoActivity) {
      return false;
    }
    switch (uri.queryParameters[_paramAction]) {
      case _activityShowResult:
        final value = context.variables.current[demoInputVariable];
        try {
          final url = Uri.tryParse(value);
          final data = jsonDecode(url != null ? await fetch(url) : value);

          if (data is! Map<String, dynamic>) {
            throw Exception('Not a map object');
          }
          _navigationManager.push(
            MaterialPageRoute(builder: (_) => ShowPage(data)),
          );
        } catch (e) {
          showSnackBar('Parsing error: $e');
        }
        break;
      case _activityPaste:
        final cdata = await Clipboard.getData(Clipboard.kTextPlain);
        final text = cdata?.text;
        if (text == null) {
          showSnackBar('Clipboard is empty');
        }
        context.variableManager.updateVariable(demoInputVariable, text);
        break;
      case _activityOpenQr:
        // TODO: support scanning QR-code
        showSnackBar('Action is not supported yet');
        break;
      case _activityOpenFile:
        // TODO: support opening file
        showSnackBar('Action is not supported yet');
        break;
      default:
        return false;
    }

    return true;
  }

  void showSnackBar(String text) {
    ScaffoldMessenger.of(_navigator.currentContext!).hideCurrentSnackBar();
    ScaffoldMessenger.of(_navigator.currentContext!).showSnackBar(
      SnackBar(
        showCloseIcon: true,
        content: Text(text),
        behavior: SnackBarBehavior.floating,
      ),
    );
  }
}
