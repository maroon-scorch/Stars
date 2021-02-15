/**
 * Handles the show and removal of Modal Logic in the GUI
 */

// Initial Status of the Modal - check if it's suppsoed to be displayed or not - default: none
let initial = sessionStorage.hasOwnProperty("modalStatus") ? sessionStorage.getItem("modalStatus") : "none";
// The overlay div and the modal div in the HTML
let overlay = document.getElementById("modal-overlay");
let modal = document.getElementById("modal-package");

/**
 * Hide the two divs and sets the display status of the Modal to none
 *
 */
function removeModal() {
    overlay.style.display = "none";
    modal.style.display = "none";
    sessionStorage.setItem("modalStatus", "none");
}

/**
 * Sets the display status of the Modal to block
 *
 */
function showModal() {
    sessionStorage.setItem("modalStatus", "block");
}

/**
 * Sets the display status of the Modal to the displayType indicated - for initialization step after refresh.
 *
 * @param displayType - the type being displayed - either "none" or "block"
 */
function initialize(displayType) {
    overlay.style.display = displayType;
    modal.style.display = displayType;
}

// Initialize the state
initialize(initial);
// Clicking the Overlay removes the Modal
document.querySelector("#modal-overlay").addEventListener("click", removeModal);
// Clicking the Exit Sign removes the Modal
document.querySelector(".modal-exit").addEventListener("click", removeModal);

// All Forms with class name "star-forms" when submitted causes the Modal to pop up
let allForms = document.querySelectorAll(".star-forms");
Array.from(allForms).forEach(form => {
    form.addEventListener("submit", showModal);
});