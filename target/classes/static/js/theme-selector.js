/**
 * Handles the theme selection (light mode, dark mode) of the GUI
 */

// The list of themes to select from
const themeList = [
    {
        className: "light"
    },
    {
        className: "dark"
    }
]

// Initialization - check what the current theme is after refresh; default: light mode
let currentIndex = sessionStorage.hasOwnProperty('currentIndex') ? sessionStorage.getItem('currentIndex') : 0;
let themeLength = themeList.length;


/**
 * When the icon is clicked , the current Index will shift one right modularly in the list of current themes
 * and apply the changes accordingly
 *
 */
function selectRight () {
    currentIndex = (currentIndex + 1) % themeLength;
    let currentTheme = themeList[currentIndex]
    themeToDisplay(currentTheme);
    sessionStorage.setItem('currentIndex', currentIndex);
}

/**
 * Switches the theme of the HTML to the currentTheme indicated by changing the class name of the
 * body.
 *
 * @param currentTheme - the theme indicated
 */
function themeToDisplay(currentTheme) {
    console.log("Switching to " + currentTheme.className + " Mode");
    document.body.className = currentTheme.className;
}

/**
 * Initializes the theme of the webpage after refresh to whatever the session storage or default offers.
 *
 */
function initializeTheme() {
    let currentTheme = themeList[currentIndex];
    themeToDisplay(currentTheme);
}

initializeTheme();
document.querySelector(".theme-selection").addEventListener("click", selectRight);