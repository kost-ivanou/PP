document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('uploadForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData();
        const fileInput = document.getElementById('fileInput');
        const action = document.querySelector('input[name="action"]:checked').value;

        if (fileInput.files.length > 0) {
            formData.append('file', fileInput.files[0]);
            formData.append('action', action);

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
                const originalFilename = fileInput.files[0].name.replace(/(\.[^\.]+)$/, ''); // Убираем расширение
                if (action === 'zip') {
                    a.download = 'processed_' + originalFilename + '.zip';
                } else if (action === 'rar') {
                    a.download = 'processed_' + originalFilename + '.rar';
                } else if (action === 'encrypt') {
                    a.download = 'processed_' + originalFilename; // Шифрование без расширения
                } else {
                    a.download = 'processed_' + originalFilename; // Обработанный файл
                }
                document.body.appendChild(a);
                a.click();
                window.URL.revokeObjectURL(url);
                document.getElementById('result').innerText = 'Файл успешно загружен!';
            })
            .catch(error => {
                document.getElementById('result').innerText = 'Произошла ошибка: ' + error.message;
            });
        }
    });
});