<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/main.css">
  <link rel="stylesheet" href="css/theme.css">
  <link rel="stylesheet" href="css/fontawesome/css/all.css">
  </head>
  <body class="light">
  <div class="scene"></div>
  <div class="theme-selection">
    <button class="theme-left">
      <i class="fas fa-arrow-circle-left"></i>
    </button>
    <span class="light-mode" id="current-theme">
      <img src="assets/day-unselected.png" id="current-theme-image">
    </span>
    <button class="theme-right">
      <i class="fas fa-arrow-circle-right"></i>
    </button>
  </div>
    ${content}
     <!-- Again, we're serving up the unminified source for clarity. -->
     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/theme-selector.js"></script>
  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>