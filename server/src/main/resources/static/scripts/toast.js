document.addEventListener('DOMContentLoaded', function() {
	document.querySelectorAll('.toast').forEach(function(toastEl) {
		new bootstrap.Toast(toastEl).show();
	});
});