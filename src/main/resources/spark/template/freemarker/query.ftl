<#assign content>
<div class="query-wrapper">
  <div class="query">

<h1> Query </h1>

<p> for neighbors
<form method="GET" action="/neighbors">
  <input type="submit">
</form>
</p>

<p> by radius
<form method="GET" action="/radius">
  <input type="submit">
</form>
</p>

  <form>
    Light/Dark
    <input type="submit">
  </form>
  <p>${message}</p>
  </div>
</div>

</#assign>
<#include "main.ftl">