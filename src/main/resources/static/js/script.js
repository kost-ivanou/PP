document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('uploadForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData();
        const fileInput = document.getElementById('fileInput');

        if (fileInput.files.length > 0) {
            formData.append('file', fileInput.files[0]);

            fetch('/api/upload', {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Ошибка при загрузке файла');
                }
                return response.blob();
            })
            .then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.style.display = 'none';
                a.href = url;
                a.download = 'processed_' + fileInput.files[0].name; // Имя для скачиваемого файла
                document.body.appendChild(a);
                a.click(); // Имитируем клик для скачивания
                window.URL.revokeObjectURL(url); // Освобождаем URL-объект
                document.getElementById('result').innerText = 'Файл успешно загружен!';
            })
            .catch(error => {
                document.getElementById('result').innerText = 'Произошла ошибка: ' + error.message;
            });
        }
    });
});