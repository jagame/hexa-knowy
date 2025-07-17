
	document.addEventListener('DOMContentLoaded', function () {
	const answeredButtons = document.querySelectorAll('.exercise-option');
	const checkButton = document.getElementById('answers-button');
	let answered = false;
	let selectedOption = null;

	// Guardamos qué opción seleccionó el usuario
	answeredButtons.forEach(btn => {
	btn.addEventListener('click', function () {
	if (answered) return;

	// Desmarcar todas
	answeredButtons.forEach(b => b.classList.remove('selected'));

	// Marcar la actual
	btn.classList.add('selected');
	selectedOption = btn;
});
});

	// Escuchar el clic en el botón externo
	checkButton.addEventListener('click', function () {
	if (answered || !selectedOption) return;

	answered = true;

	answeredButtons.forEach(b => {
	const isCorrect = b.getAttribute('data-correct') === 'true';
	if (b === selectedOption) {
	if (isCorrect) {
	b.classList.add('correct');
} else {
	b.classList.add('incorrect');
}
} else if (isCorrect) {
	b.classList.add('correct');
}
});
});
});
