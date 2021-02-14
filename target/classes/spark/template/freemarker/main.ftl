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
  <link rel="stylesheet" href="css/fontawesome/css/font-awesome.min.css">
  </head>
  <body>
  <div class="scene"></div>
  <div class="daynight-selection">
    <span class="daynight-left">
      <i class="fa fa-arrow-right"></i>
    </span>
    <span class="night-mode">
      <img src="assets/night-unselected.png" alt="day-selected">
    </span>
    <span class="daynight-right">
      <i class="fa fa-arrow-right"></i>
    </span>
  </div>
    ${content}
     <!-- Again, we're serving up the unminified source for clarity. -->
     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/day-night-mode.js"></script>
  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>