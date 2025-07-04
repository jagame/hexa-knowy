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
		span.className = 'badge badge-info mr-1 mb-1 selected-language';
		span.style.cursor = 'pointer';
		span.textContent = language;
		span.onclick = () => removeLanguage(language);
		return span;
	}

	function removeLanguage(language) {
		selectedLanguagesSet.delete(language);
		document.querySelectorAll(`[data-language="${language}"]`)
			.forEach(item => item.remove());
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

	const profilePicId = document.getElementById('profile-pic-id');
	const profilePicture = document.getElementById('profilePicture');
	document.querySelectorAll('.avatar-selectable')
		.forEach(avatar => avatar.addEventListener('click', event => {
			profilePicId.value = event.target.dataset.avatarId
			profilePicture.src = event.target.src
		}))
});