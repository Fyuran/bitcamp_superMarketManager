/**
 * 
 */


document.getElementById("navbar-ham-btn").addEventListener("mousedown", handleHamburgerMenu);

screen.orientation.addEventListener("change", () => {
	
	let menu = document.getElementById("hamburger-menu");
	if(menu != null) {
		menu.classList.remove("hamburger-menu-open");
		menu.style.width = 0;
	}
});

function handleHamburgerMenu() {
	const navbar = document.getElementById("nav");
	const navbarSize = navbar.getBoundingClientRect();

	let menu = document.getElementById("hamburger-menu");
	if(menu.classList.toggle("hamburger-menu-open")) {
		setTimeout(() => {
			menu.style.top = navbarSize.height + "px";
			menu.style.width = "20vw";
		}, 0);
	} else {
		setTimeout(() => {	
			menu.style.width = 0;
		}, 0);
	}


	
}