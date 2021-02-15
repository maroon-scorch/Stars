<#assign content>
<div class="query-wrapper">
  <div class="query">

<h1> Query </h1>

<p> for neighbors
<form method="GET" action="/neighbors">
  <input type="submit">
</form>
</p>

<p> by radius coordinates
<form method="GET" action="/radius">
      Radius
      <input type="text" name="radius-input">
      X Coordinate
      <input type="text" name="x-input">
      Y Coordinate
      <input type="text" name="y-input">
      Z Coordinate
      <input type="text" name="z-input">
  <input type="submit">
</form>
</p>

<p> by radius name
<form method="GET" action="/radius">
  Radius
  <input type="text" name="radius-input">
  Name of Star
  <input type="text" name="name-input">
          <input type="submit">
</form>
</p>

  <p>${message}</p>
  </div>
</div>
</#assign>
<#include "main.ftl">