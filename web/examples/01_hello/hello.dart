import 'dart:html';
import 'dart:async';

int sleepTime = 100;

void main() async {
  await loadingBar();
  await loadingBar();
  await loadingBar();
  writeAsync('Hello, World!');
}

loadingBar() async {
  await writeAsync('|');
  await writeAsync('/');
  await writeAsync('-');
  await writeAsync('\\');
  await writeAsync('|');
  await writeAsync('/');
  await writeAsync('-');
  await writeAsync('\\');
}

writeAsync(String text) async {
  var chars = new Iterable.generate(text.length, (i) => text.substring(0, i+1));
  for(String char in chars) {
    await sleep();
    write(char) ;
  }
}

Future sleep() {
  Completer completer = new Completer();
  new Timer(new Duration(milliseconds: sleepTime), () => completer.complete());
  return completer.future;
}

write(var title) {
  querySelector('#title').text = title;
}