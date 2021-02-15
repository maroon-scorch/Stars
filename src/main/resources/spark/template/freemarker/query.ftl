<#assign content>
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

    <div class="theme-selection"></div>

    <div id="modal-overlay"></div>
        <div id="modal-package">
            <div class="modal-header">TITLE</div>
            <div class="modal-exit">X</div>
            <div class="modal-body">
                ${message}
            </div>
        </div>


<div class="query-wrapper">
  <div class="query">

<div id="star-title"><h1> Searching for Stars! </h1></div>

<label for="searches">Type of Search: </label>
<select id="searches" class="search-type">
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

<div id="neighbors-coordinates" class="possible-form"> Search by Neighbors Coordinates
<form method="GET" action="/neighbors" class="star-forms">
    <label>Number of Neighbors
          <input type="number" name="radius-input"><br></label>
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
<form method="GET" action="/neighbors" class="star-forms">
  <label>Number of Neighbors
    <input type="number" name="radius-input"><br></label>
    <label>Name of Star
          <input type="text" name="name-input"><br></label>
  <input type="submit">
</form>
</div>

<div id="radius-coordinates" class="possible-form"> Search by Radius Coordinates
<form method="GET" action="/radius" class="star-forms">
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

<div id="radius-name" class="possible-form"> Search by radius name
<form method="GET" action="/radius" class="star-forms">
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
</#assign>
<#include "main.ftl">