document.addEventListener('DOMContentLoaded', function () {
	const addLanguagesButton = document.getElementById('chooseLanguage');
	const removeLanguagesButton = document.getElementById('removeLanguage');
	const languagesSelect = document.getElementById('languages');
	const selectedDiv = document.getElementById('selectedLanguages');

	const selectedLanguagesSet = new Set();
	addLanguagesButton.addEventListener('click', addSelectedLanguages);

	function addSelectedLanguages() {
		const selectedOptions = Array.from(languagesSelect.options)
			.filter(option => option.selected);

		selectedOptions.forEach(option => {
			if (option.selected) {
				const language = option.value.trim()
				if (selectedLanguagesSet.has(language)) {
					return;
				}
				selectedLanguagesSet.add(language);
				selectedDiv.appendChild(buildSpanLanguageOptions(language));
				selectedDiv.appendChild(buildInputLanguageOptions(language));
			}
			option.selected = false
		});
	}

	function buildSpanLanguageOptions(language) {
		const span = document.createElement('span');
		span.dataset.language = language;
		span.className = 'badge badge-info my-3 mx-2 selected-language';
		span.style.cursor = 'pointer';
		span.style.fontWeight = 'normal';
		span.style.fontSize = '16px';
		span.textContent = language;
		return span;
	}

	function buildInputLanguageOptions(language) {
		const input = document.createElement('input');
		input.dataset.language = language;
		input.className = 'selected-language';
		input.type = 'hidden';
		input.name = 'languages';
		input.value = language;
		return input;
	}

	removeLanguagesButton.addEventListener('click', () => removeLanguages());

	function removeLanguages() {
		selectedDiv.querySelectorAll('.selected-language').forEach(element => {
			element.remove()
		});
		selectedLanguagesSet.clear()
	}

	document.getElementById('selectedLanguages')
		.addEventListener('click', (event) => {
			const target = event.target;
			if (!target.classList.contains('selected-language')) {
				return;
			}

			const language = target.dataset.language;
			if (language) {
				removeLanguage(language);
			}
		})

	function removeLanguage(language) {
		selectedLanguagesSet.delete(language);
		document.querySelectorAll(`[data-language="${language}"]`)
			.forEach(item => item.remove());
	}

	const profilePicId = document.getElementById('profile-pic-id');
	const profilePicture = document.getElementById('profilePicture');
	document.querySelectorAll('.avatar-selectable')
		.forEach(avatar => avatar.addEventListener('click', event => {
			profilePicId.value = event.target.dataset.avatarId
			profilePicture.src = event.target.src
		}))
});