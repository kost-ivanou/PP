document.addEventListener('DOMContentLoaded', function() {
document.getElementById('uploadForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Отменяем стандартное поведение формы

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
                return response.text(); // Или response.json(), если возвращаете JSON
            })
            .then(result => {
                document.getElementById('result').innerText = 'Файл успешно загружен! Результат: ' + result;
            })
            .catch(error => {
                document.getElementById('result').innerText = 'Произошла ошибка: ' + error.message;
            });
        }
 });
 }