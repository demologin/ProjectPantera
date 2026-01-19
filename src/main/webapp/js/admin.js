// ===== Общие утилиты =====

function qsAll(selector) {
    return document.querySelectorAll(selector);
}

function byId(id) {
    return document.getElementById(id);
}

// ===== Сброс всех строк =====

function resetAllRows() {
    qsAll("[id^='edit-']").forEach(e =>
        e.classList.add("hidden")
    );

    qsAll("[id^='view-']").forEach(e =>
        e.classList.remove("hidden")
    );

    qsAll(".saveBtn").forEach(b =>
        b.classList.add("hidden")
    );
}

// ===== Кнопка Добавить =====

function disableAddButton(disable) {
    const btn = byId("addBtn");
    if (btn) btn.disabled = disable;
}

// ===== Редактирование строки =====

function enableRowEdit(id) {
    const addForm = byId("addForm");
    if (addForm && !addForm.classList.contains("hidden")) return;

    resetAllRows();

    byId("view-" + id)?.classList.add("hidden");
    byId("edit-" + id)?.classList.remove("hidden");

    disableAddButton(true);
}

function cancelRowEdit(id) {
    const editBlock = byId("edit-" + id);

    // вернуть исходные значения
    editBlock?.querySelectorAll("[data-original]").forEach(el => {
        el.value = el.dataset.original;
    });

    editBlock?.querySelector(".saveBtn")?.classList.add("hidden");

    editBlock?.classList.add("hidden");
    byId("view-" + id)?.classList.remove("hidden");

    disableAddButton(false);
}

// ===== Добавление =====

function showAddForm() {
    resetAllRows();

    qsAll(".editBtn").forEach(b => b.disabled = true);

    byId("addBtn")?.classList.add("hidden");
    byId("addForm")?.classList.remove("hidden");
}

function cancelAddForm() {
    qsAll(".editBtn").forEach(b => b.disabled = false);

    byId("addForm")?.classList.add("hidden");
    byId("addBtn")?.classList.remove("hidden");
}

function onUserFieldChange(id) {
    const editBlock = document.getElementById("edit-" + id);
    if (!editBlock) return;

    const saveBtn = editBlock.querySelector(".saveBtn");
    if (!saveBtn) return;

    const passwordInput = editBlock.querySelector("input[name='password']");

    // 👇 если это НЕ пользователь (квесты, другое)
    if (!passwordInput) {
        saveBtn.classList.remove("hidden");
        return;
    }

    // 👇 если это пользователь — проверяем пароль
    if (passwordInput.value.trim().length === 0) {
        saveBtn.classList.add("hidden");
    } else {
        saveBtn.classList.remove("hidden");
    }
}