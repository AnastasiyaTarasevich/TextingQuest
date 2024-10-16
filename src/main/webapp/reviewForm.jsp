<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="bg-dark text-light p-4 shadow-lg mt-5 ">
    <h2 class="text-center mb-4">Оставьте отзыв о квесте</h2>
    <form action="submitReview" method="post">
        <!-- Имя пользователя -->
        <div class="mb-3">
            <label for="name" class="form-label">Ваше имя:</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Введите ваше имя" >
        </div>

        <!-- Оценка (звезды) -->
        <div class="mb-3">
            <label for="rating" class="form-label">Оценка:</label>
            <select class="form-select" id="rating" name="rating" >
                <option value="">Выберите оценку</option>
                <option value="5">⭐⭐⭐⭐⭐ (Отлично)</option>
                <option value="4">⭐⭐⭐⭐ (Хорошо)</option>
                <option value="3">⭐⭐⭐ (Средне)</option>
                <option value="2">⭐⭐ (Плохо)</option>
                <option value="1">⭐ (Ужасно)</option>
            </select>
        </div>

        <!-- Текст отзыва -->
        <div class="mb-3">
            <label for="description" class="form-label">Ваш отзыв:</label>
            <textarea class="form-control" id="description" name="description" rows="5" placeholder="Поделитесь своими впечатлениями" ></textarea>
        </div>

        <!-- Кнопка отправки -->
        <button type="submit" class="btn btn-warning btn-lg">Отправить отзыв</button>
    </form>
</div>