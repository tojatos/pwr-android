import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Pesel Checker',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      darkTheme: ThemeData(brightness: Brightness.dark),
      themeMode: ThemeMode.dark,
      debugShowCheckedModeBanner: false,
      home: PeselChecker(title: 'Pesel Checker'),
    );
  }
}

class PeselChecker extends StatefulWidget {
  PeselChecker({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _PeselCheckerState createState() => _PeselCheckerState();
}

class _PeselCheckerState extends State<PeselChecker> {
  var weights = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1];
  String _pesel = "";
  bool peselValid() => _pesel.length == 11;
  int calculateChecksum() {
    var x = _pesel.split('').map(int.parse).toList();
    var sum = 0;
    for (var i = 0; i < 11; i++) {
      sum += weights[i] * x[i];
    }
    return sum % 10;
  }

  bool checksumValid() => peselValid() && calculateChecksum() == 0;
  String birthDate() {
    if (_pesel.length < 6) return 'Brak informacji';

    var x = _pesel.split('').map(int.parse).toList();
    var year = 1900 + x[0] * 10 + x[1];
    if (x[2] >= 2 && x[2] < 8) year += (x[2] / 2).floor() * 100;
    if (x[2] >= 8) year -= 100;

    var month = (x[2] % 2) * 10 + x[3];
    var day = x[4] * 10 + x[5];

    return '$day $month $year';
  }

  String sex() {
    if (_pesel.length < 10) return 'Brak informacji';

    var x = _pesel.split('').map(int.parse).toList();
    return (x[9] % 2 == 1) ? 'M' : 'K';
  }

  void _setPesel(String value) {
    setState(() {
      _pesel = value;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Container(
        margin: EdgeInsets.only(left: 20, right: 20),
        child: ListView(
          children: <Widget>[
            TextField(
              keyboardType: TextInputType.number,
              onChanged: _setPesel,
              decoration: InputDecoration(
                  border: InputBorder.none, hintText: 'Wpisz pesel'),
            ),
            Row(children: <Widget>[
              Text('Poprawna długość: '),
              Icon(peselValid() ? Icons.check : Icons.close)
            ]),
            Text('Data urodzenia: ${birthDate()}'),
            Text('Płeć: ${sex()}'),
            Row(children: <Widget>[
              Text('Poprawna suma kontrolna: '),
              Icon(checksumValid() ? Icons.check : Icons.close)
            ]),
          ],
        ),
      ),
    );
  }
}
