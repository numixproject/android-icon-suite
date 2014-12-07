$(window).load(function() {
var wallpaper = document.getElementById('wallpaper');
var apply = document.getElementById('apply');
var submit = document.getElementById('submit');


wallpaper.onclick = function() {
    Android.showBackground();
};
apply.onclick = function() {
    Android.applyTheme();
};
submit.onclick = function() {
    Android.requestIcons();
};
});