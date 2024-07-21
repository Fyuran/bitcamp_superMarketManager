let mediaWidthMatcher = window.matchMedia("(max-width: 1000px)"); //media matcher for carousel options
mediaWidthMatcher.addEventListener("change", handlePageSize);

document.getElementById("navbar-ham-btn").addEventListener("mousedown", handleHamburgerMenu); //click on hamburger event
document.getElementById("carouselTutorial").addEventListener("slid.bs.carousel", event => handleCarouselCaptions(event)); //carousel slide change caption

window.addEventListener("load", handlePageSize); //manage on load stuff

screen.orientation.addEventListener("change", () => {
	
	handlePageSize();
	
	let menu = document.getElementById("hamburger-menu");
	if(menu != null) {
		let hamburger = document.getElementById("navbar-ham-btn");
		setTimeout(() => {
			menu.style.height = 0;
			menu.classList.remove("hamburger-menu-open");
			hamburger.style.transform = "rotate(0)";
		}, 0);
	}
});

function handleHamburgerMenu() {
	const navbar = document.getElementById("nav");
	const navbarSize = navbar.getBoundingClientRect();

	let menu = document.getElementById("hamburger-menu");
	let hamburger = document.getElementById("navbar-ham-btn");
	
	let size = 0;
	for(child of menu.children) {
		let height = parseInt(window.getComputedStyle(child).getPropertyValue("height"));
		size += height;
	}
	if(menu.classList.toggle("hamburger-menu-open")) {
		setTimeout(() => {
			hamburger.style.transform = "rotate(90deg)";
			menu.style.top = navbarSize.height + "px";
			menu.style.height = size + "px";
		}, 0);
	} else {
		setTimeout(() => {
			hamburger.style.transform = "rotate(0)";
			menu.style.height = 0;
		}, 0);
	}	
}

function handleCarouselCaptions(event) {
	let captions = document.querySelectorAll(".caption");
	
	if(captions[event.from] != null) {
		captions[event.from].style.display = "none";
	}
	if(captions[event.to] != null) {
		captions[event.to].style.display = "initial";
	}
}

function handlePageSize() {
	let visibleCarousel = document.querySelector(".carousel-item.active");
	let captions = document.querySelectorAll(".caption");
	if(visibleCarousel != null) {
		visibleCarousel.classList.remove("active");
	}
	
	if(mediaWidthMatcher.matches) {
		let videoItem = document.getElementById("carousel-video");
		videoItem.classList.add("active");
		
		for(let caption of captions) {
			caption.style.display = "none";
		}
		captions[captions.length - 1].style.display = "initial";
	} else {
		let firstItem = document.getElementById("carousel-first-item");
		firstItem.classList.add("active");
		
		for(let caption of captions) {
			caption.style.display = "none";
		}
		captions[0].style.display = "initial";
	}
}