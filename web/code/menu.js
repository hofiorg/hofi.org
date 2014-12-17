  var template = document.querySelector('#t');

  var dartmenu = [
    {label: 'Prozess starten', title: 'Dart - Prozess starten',      url: 'dart/process.html', tag:'dart_process', titlefull:'Prozess starten - System Prozess mit Dart unter Windows starten'},
    {label: 'Videos',          title: 'Dart - Videos',               url: 'dart/videos.html',  tag:'dart_videos',  titlefull:'Videos über Dart'},
    {label: 'where',           title: 'Dart - iterate a collection', url: 'dart/where.html',   tag:'dart_where',   titlefull:'where - Collection mit einer where-Bedingung iterieren'},
  ];

  var htmlmenu = [
    {label: 'Abgerundete Bilder', title: 'CSS - Abgerundete Bilder',         url: 'css/round_image.html',          tag:'css_round_image',      titlefull:'Abgerundete Bilder mit CSS'},
    {label: 'Google+ Kommentare', title: 'Google Plus Kommentare einbinden', url: 'css/google_plus_comments.html', tag:'google_plus_comments', titlefull:'Google Plus Kommentare einbinden'},
  ];  

  var javascriptmenu = [
    {label: 'httpGet',    title: 'Javascript - httpGet',    url: 'javascript/http_get.html',    tag:'javascript_http_get',    titlefull:'httpGet - Funktion für synchronen XMLHttpRequest'},
    {label: 'replaceAll', title: 'Javascript - replaceAll', url: 'javascript/replace_all.html', tag:'javascript_replace_all', titlefull:'replaceAll - Funktion um alle Vorkommnisse eines Strings zu ersetzen'},
  ];    

  var polymermenu = [
    {label: 'Paper Icon Buttons', title: 'Polymer - Paper Icon Buttons', url: 'polymer/paper_icon_buttons.html', tag:'polymer_paper_icon_buttons', titlefull:'Paper Icon Buttons - paper-icon-button mit Transition-Effekt'},
    {label: 'Scrollbarer Header', title: 'Polymer - Scrollbarer Header', url: 'polymer/header.html',             tag:'polymer_header',             titlefull:'Scrollbarer Header - core-scroll-header-panel'},
    {label: 'Videos',             title: 'Polymer - Videos',             url: 'polymer/videos.html',             tag:'polymer_videos',             titlefull:'Videos über Polymer'},
  ];  

  var vorlesung_1 = [
    {label: 'Unterlagen', title: 'Übungsunterlagen',        url: 'vorlesung_1/unterlagen.html', tag:'vorlesung_1_unterlagen'},
    {label: '16.10.2014', title: 'Vorlesung am 16.10.2014', url: 'vorlesung_1/2014_10_16.html', tag:'vorlesung_1_2014_10_16'},
    {label: '23.10.2014', title: 'Vorlesung am 23.10.2014', url: 'vorlesung_1/2014_10_23.html', tag:'vorlesung_1_2014_10_23'},
    {label: '30.10.2014', title: 'Vorlesung am 30.10.2014', url: 'vorlesung_1/2014_10_30.html', tag:'vorlesung_1_2014_10_30'},
    {label: '06.11.2014', title: 'Vorlesung am 06.11.2014', url: 'vorlesung_1/2014_11_06.html', tag:'vorlesung_1_2014_11_06'},
    {label: '13.11.2014', title: 'Vorlesung am 13.11.2014', url: 'vorlesung_1/2014_11_13.html', tag:'vorlesung_1_2014_11_13'},
    {label: '27.11.2014', title: 'Vorlesung am 27.11.2014', url: 'vorlesung_1/2014_11_27.html', tag:'vorlesung_1_2014_11_27'},
    {label: '11.12.2014', title: 'Vorlesung am 11.12.2014', url: 'vorlesung_1/2014_12_11.html', tag:'vorlesung_1_2014_12_11'},
    {label: '18.12.2014', title: 'Vorlesung am 18.12.2014', url: 'vorlesung_1/2014_12_18.html', tag:'vorlesung_1_2014_12_18'},
  ];    

  var vorlesung_2 = [
    {label: 'Unterlagen', title: 'Übungsunterlagen',        url: 'vorlesung_2/unterlagen.html', tag:'vorlesung_2_unterlagen'},
    {label: '16.10.2014', title: 'Vorlesung am 16.10.2014', url: 'vorlesung_2/2014_10_16.html', tag:'vorlesung_2_2014_10_16'},
    {label: '30.10.2014', title: 'Vorlesung am 30.10.2014', url: 'vorlesung_2/2014_10_30.html', tag:'vorlesung_2_2014_10_30'},
    {label: '06.11.2014', title: 'Vorlesung am 06.11.2014', url: 'vorlesung_2/2014_11_06.html', tag:'vorlesung_2_2014_11_06'},
    {label: '13.11.2014', title: 'Vorlesung am 13.11.2014', url: 'vorlesung_2/2014_11_13.html', tag:'vorlesung_2_2014_11_13'},
    {label: '27.11.2014', title: 'Vorlesung am 27.11.2014', url: 'vorlesung_2/2014_11_27.html', tag:'vorlesung_2_2014_11_27'},
  ];      

  var webcomponentsmenu = [
    {label: 'github-gist',              title: 'WebComponents - github-gist',              url: 'webcomponents/github_gist.html',                       
                                        tag:'webcomponents_github_gist',                   titlefull: 'github-gist - Code-Snippets (gists) auf einer Website darstellen'},
    {label: 'google-analytics-tracker', title: 'WebComponents - google-analytics-tracker', url: 'webcomponents/google_analytics_tracker.html',
                                        tag:'webcomponents_google_analytics_tracker',      titlefull:'google-analytics-tracker - Webseiten-Aufrufe mit GoogleAnalytics tracken'},
    {label: 'google-youtube',           title: 'WebComponents - google-youtube',           url: 'webcomponents/google_youtube.html',
                                        tag:'webcomponents_google_youtube',                titlefull:'google-youtube - YouTube video playback'},                                        
    {label: 'polymer-github-card',      title: 'WebComponents - polymer-github-card',      url: 'webcomponents/polymer_github_card.html',
                                        tag:'webcomponents_polymer_github_card',           titlefull:'polymer-github-card - Karte eines GitHub-Profils anzeigen'},                                        
  ];     

  var menus = [
    {id: 'dartmenu',          label: 'Dart',             sub: dartmenu},
    {id: 'htmlmenu',          label: 'HTML + CSS',       sub: htmlmenu},
    {id: 'javascriptmenu',    label: 'Javaccript',       sub: javascriptmenu},
    {id: 'polymermenu',       label: 'Polymer',          sub: polymermenu},
    {id: 'vorlesung_1',       label: 'Vorlesung WI - 1', sub: vorlesung_1, labelfull:'Vorlesung Wirtschaftsinformatik - 1'},
    {id: 'vorlesung_2',       label: 'Vorlesung WI - 2', sub: vorlesung_2, labelfull:'Vorlesung Wirtschaftsinformatik - 2'},
    {id: 'webcomponentsmenu', label: 'WebComponents',    sub: webcomponentsmenu},
  ];

  template.menus = menus;
