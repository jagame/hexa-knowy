document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll("[fake-component]").forEach(async element => {
        const file = element.getAttribute("fake-component");

        try {
            const response = await fetch(file);
            if (!response.ok) {
                throw new Error(`No se pudo cargar ${file}`);
            }
            const html = await response.text();
            element.innerHTML = html;
        } catch (err) {
            element.innerHTML = `<p style="color: red;">Error: ${err.message}</p>`;
        }
    });
});