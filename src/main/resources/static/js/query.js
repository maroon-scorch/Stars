/**
 * Handles which Forms of the Radius and Neighbors search is selected in the GUI
 */

// Initial/Default Values for the SearchType and Input Mode
let searchType = "naive-radius";
let inputMode = "coordinates";

/**
 * Reorganizes which Form is displayed based on a change in its SearchType
 *
 * @param e - The event of what happened.
 */
function selectSearchType(e) {
    removeForm(searchType, inputMode)
    searchType = e.target.value;
    updateSearchMode(searchType, inputMode);
}

/**
 * Reorganizes which Form is displayed based on a change in its InputMode
 *
 * @param e - The event of what happened.
 */
function selectInputMode(e) {
    removeForm(searchType, inputMode)
    inputMode = e.target.value;
    updateSearchMode(searchType, inputMode);
}

/**
 * Removes the form indicated (current form usually) from display
 *
 * @param searchType - the search type of the form
 * @param inputMode - the input mode of the form
 */
function removeForm(searchType, inputMode) {
    let designatedName = searchType + "-" + inputMode;
    let form = document.getElementById(designatedName);
    form.style.display = "none";
}

/**
 * Change the form indicated (current form usually) from display to the one matching the two inputs
 *
 * @param searchType - the search type of the form
 * @param inputMode - the input mode of the form
 */
function updateSearchMode(searchType, inputMode) {
    let designatedName = searchType + "-" + inputMode;
    let form = document.getElementById(designatedName);
    form.style.display = "block";
}

// Initialization step
updateSearchMode(searchType, inputMode);
// Adding the event listeners to the two selection box
document.querySelector(".search-type").addEventListener("change", selectSearchType);
document.querySelector(".input-mode").addEventListener("change", selectInputMode);