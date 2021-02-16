<#assign content>
<#--    The background for the GUI Display  -->
    <div class="current-mode">
        <div class="current-title"></div>
        <div class="current-background"></div>
        <div class="current-midground">
            <div class="cloud1"></div>
            <div class="cloud2"></div>
            <div class="cloud3"></div>
        </div>
        <div class="current-foreground"></div>
    </div>
<#--  xx  The background for the GUI Display  xx  -->

<#--    The Selection Between Darkmode and Lightmode  -->
    <div class="theme-selection"></div>
<#--  xx  The Selection Between Darkmode and Lightmode  xx  -->

<#--    The Modal to display the Results -->
    ${display}
<#--  xx  The Modal to display the Results xx -->

<#--    The Query for the User to input  -->
<div class="query-wrapper">
  <div class="query">

<div id="star-title"><h1> Searching for Stars! </h1></div>

<label for="searches">Type of Search: </label>
<select id="searches" class="search-type">
    <option value="naive-radius">Naive Radius Search</option>
    <option value="naive-neighbors">Naive Neighbors Search</option>
  <option value="radius">Radius Search</option>
  <option value="neighbors">Neighbors Search</option>
</select>
<br><br>
<label for="input-mode">Input Mode: </label>
<select id="input-mode" class="input-mode">
  <option value="coordinates">Coordinate Input Mode</option>
  <option value="name">Name Input Mode</option>
</select>
<br><br>

  <div id="naive-neighbors-coordinates" class="possible-form"> Search by Naive Neighbors Coordinates
      <form method="GET" action="/naive-neighbors-cord" class="star-forms" autocomplete="off" >
          <label>Number of Neighbors
              <input type="number" name="count-input"><br></label>
          <label>X Coordinate
              <input type="number" name="x-input"><br></label>
          <label>Y Coordinate
              <input type="number" name="y-input"><br></label>
          <label>Z Coordinate
              <input type="number" name="z-input"><br></label>
          <input type="submit">
      </form>
  </div>

  <div id="naive-neighbors-name" class="possible-form"> Search by Naive Neighbors Name
      <form method="GET" action="/naive-neighbors-name" class="star-forms" autocomplete="off" >
          <label>Number of Neighbors
              <input type="number" name="count-input"><br></label>
          <label>Name of Star
              <input type="text" name="name-input"><br></label>
          <input type="submit">
      </form>
  </div>

  <div id="naive-radius-coordinates" class="possible-form"> Search by Naive Radius Coordinates
      <form method="GET" action="/naive-radius-cord" class="star-forms" autocomplete="off" >
          <label>Radius
              <input type="number" name="radius-input"><br>
          </label>
          <label>X Coordinate
              <input type="number" name="x-input"><br>
          </label>
          <label>Y Coordinate
              <input type="number" name="y-input"><br>
          </label>
          <label>Z Coordinate
              <input type="number" name="z-input"><br>
          </label>
          <input type="submit">
      </form>
  </div>

  <div id="naive-radius-name" class="possible-form"> Search by Naive Radius Name
      <form method="GET" action="/naive-radius-name" class="star-forms" autocomplete="off" >
          <label>Radius
              <input type="number" name="radius-input"><br>
          </label>
          <label>Name of Star
              <input type="text" name="name-input"><br>
          </label>
          <input type="submit">
      </form>
  </div>

<div id="neighbors-coordinates" class="possible-form"> Search by Neighbors Coordinates
<form method="GET" action="/neighbors-cord" class="star-forms" autocomplete="off" >
    <label>Number of Neighbors
          <input type="number" name="count-input"><br></label>
    <label>X Coordinate
          <input type="number" name="x-input"><br></label>
    <label>Y Coordinate
        <input type="number" name="y-input"><br></label>
    <label>Z Coordinate
        <input type="number" name="z-input"><br></label>
  <input type="submit">
</form>
</div>

<div id="neighbors-name" class="possible-form"> Search by Neighbors Name
<form method="GET" action="/neighbors-name" class="star-forms" autocomplete="off" >
  <label>Number of Neighbors
    <input type="number" name="count-input"><br></label>
    <label>Name of Star
          <input type="text" name="name-input"><br></label>
  <input type="submit">
</form>
</div>

<div id="radius-coordinates" class="possible-form"> Search by Radius Coordinates
<form method="GET" action="/radius-cord" class="star-forms" autocomplete="off" >
      <label>Radius
      <input type="number" name="radius-input"><br>
      </label>
      <label>X Coordinate
      <input type="number" name="x-input"><br>
      </label>
      <label>Y Coordinate
      <input type="number" name="y-input"><br>
      </label>
      <label>Z Coordinate
      <input type="number" name="z-input"><br>
      </label>
  <input type="submit">
</form>
</div>

<div id="radius-name" class="possible-form"> Search by Radius Name
<form method="GET" action="/radius-name" class="star-forms" autocomplete="off" >
  <label>Radius
  <input type="number" name="radius-input"><br>
  </label>
  <label>Name of Star
  <input type="text" name="name-input"><br>
  </label>
          <input type="submit">
</form>
</div>

  </div>
</div>
<#--   xx  The Query for the User to input xx  -->
</#assign>
<#include "main.ftl">