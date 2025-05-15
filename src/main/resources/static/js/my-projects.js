// my-projects.js - логика для страницы "Мои проекты"
document.addEventListener('DOMContentLoaded', function() {
    // Переключение табов
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            tabs.forEach(t => t.classList.remove('active'));
            this.classList.add('active');
            // Здесь можно добавить загрузку соответствующих проектов
        });
    });

    // Открытие модального окна
    const assignButtons = document.querySelectorAll('.project-actions .btn-primary');
    const modal = document.getElementById('assignModal');
    const closeModal = document.querySelector('.close-modal');

    assignButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            modal.style.display = 'flex';
        });
    });

    closeModal.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // Закрытие по клику вне модалки
    modal.addEventListener('click', function(e) {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    // Выбор фрилансера
    const selectButtons = document.querySelectorAll('.freelancer-item .btn-outline');
    selectButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const freelancerCard = this.closest('.freelancer-item');
            const freelancerName = freelancerCard.querySelector('h4').textContent;
            alert(`Выбран фрилансер: ${freelancerName}`);
            modal.style.display = 'none';
            // Здесь можно добавить логику назначения фрилансера
        });
    });
});