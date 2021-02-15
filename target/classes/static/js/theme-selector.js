const themeList = [
    {
        className: "light",
        modeName: "light-mode",
        image: "assets/day-unselected.png",
        hover: "assets/day-hover.png"
    },
    {
        className: "dark",
        modeName: "dark-mode",
        image: "assets/night-unselected.png",
        hover: "assets/night-hover.png"
    }
]

let currentIndex = 0;
let themeLength = themeList.length;

function selectLeft () {
    currentIndex = ((currentIndex - 1) % themeLength + themeLength) % themeLength;
    let currentTheme = themeList[currentIndex];
    themeToDisplay(currentTheme);
}

function selectRight () {
    currentIndex = (currentIndex + 1) % themeLength;
    let currentTheme = themeList[currentIndex];
    themeToDisplay(currentTheme);
}

function themeToDisplay(currentTheme) {
    console.log("Switching to " + currentTheme.className + " Mode");
    document.getElementById("current-theme").className = currentTheme.modeName;
    let imageTarget = document.getElementById("current-theme-image");
    imageTarget.src = currentTheme.image;
    imageTarget.onmouseover = function(){ imageTarget.src = currentTheme.hover };
    imageTarget.onmouseleave = function(){ imageTarget.src = currentTheme.image };
    document.body.className = currentTheme.className;
}

document.querySelector(".theme-left").addEventListener("click", selectLeft);
document.querySelector(".theme-right").addEventListener("click", selectRight);