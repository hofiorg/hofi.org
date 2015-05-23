import 'dart:html';
import 'dart:convert' show JSON;

main() {
  readJSONFile();
}

readJSONFile() async {
  String path = 'menu.json';
  var menu = JSON.decode(await HttpRequest.getString(path));
  createMenu(menu);
}

createMenu(Map menu) {
  var section = querySelector('#menu');
  menu.keys.forEach((mainmenu) {
    Element el = document.createElement('core-submenu')
      ..setAttribute('label', mainmenu)
      ..setAttribute('icon', 'expand-more');

    List submenues = menu[mainmenu];
    submenues.forEach((sub) {
      Element elSub = document.createElement('core-item')
        ..setAttribute('label', sub['label']);
      el.children.add(elSub);
    });

    section.children.add(el);
  });
}