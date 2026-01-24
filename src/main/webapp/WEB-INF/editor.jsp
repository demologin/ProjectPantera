<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quest Editor</title>
    <link rel="stylesheet" href="../static/editor.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<div class="main-wrapper">
    <aside class="editor-sidebar">
        <h2>Quest Editor</h2>
        <label>Название квеста</label>
        <input type="text" id="q-title" placeholder="Введите название..." oninput="saveToLocalStorage()">

        <label>Пролог</label>
        <textarea id="q-prologue" placeholder="О чем эта история..." oninput="saveToLocalStorage()"
                  style="height:80px;"></textarea>

        <button class="btn-publish" onclick="publishQuest()">ОПУБЛИКОВАТЬ КВЕСТ</button>

        <hr>

        <div style="display:flex; justify-content:space-between; align-items:center;">
            <h3>Параметры этапа</h3>
            <input type="number" id="node-id" value="1" style="width:70px;">
        </div>

        <label>Тип этапа</label>
        <select id="node-type" onchange="toggleOptionsVisibility()">
            <option value="common">Обычный</option>
            <option value="victory">ПОБЕДА 🏆</option>
            <option value="defeat">ПРОИГРЫШ 💀</option>
        </select>

        <label>Текст описания</label>
        <textarea id="node-text" placeholder="Опишите ситуацию..." style="height:120px;"></textarea>

        <div id="options-section">
            <div id="options-inputs">
                <label>Выборы игрока</label>
            </div>
            <button onclick="addOptionRow()"
                    style="background:#444; color:white; border:none; padding:8px; border-radius:6px; cursor:pointer; width:100%; margin-top:5px;">
                + Добавить выбор
            </button>
        </div>

        <button class="btn-main" onclick="saveNode()">Сохранить этап</button>

        <div class="sidebar-footer">
            <button class="btn-danger" onclick="clearAllData()">Очистить проект</button>
            <button class="btn-secondary" onclick="window.location='/main-menu'">Вернуться в меню</button>
        </div>
    </aside>

    <main class="nodes-canvas-area" id="canvas-container" oncontextmenu="return false;">
        <canvas id="canvas-arrows"></canvas>
        <div id="viewport">
            <div id="nodes-list"></div>
        </div>
        <div class="zoom-info" id="zoom-label">100%</div>
    </main>
</div>

<script>
    let questNodes = [];
    let draggedNode = null;
    let clickOffset = {x: 0, y: 0};
    let scale = 1, originX = 0, originY = 0;
    let isPanning = false, startPan = {x: 0, y: 0};

    window.onload = () => {
        loadFromLocalStorage();
        if ($('.opt-row').length === 0) addOptionRow();

        const container = document.getElementById('canvas-container');

        container.onwheel = (e) => {
            e.preventDefault();
            const rect = container.getBoundingClientRect();
            const mouseX = e.clientX - rect.left;
            const mouseY = e.clientY - rect.top;
            const delta = e.deltaY > 0 ? 0.9 : 1.1;
            const newScale = Math.max(0.1, Math.min(5, scale * delta));
            const worldX = (mouseX - originX) / scale;
            const worldY = (mouseY - originY) / scale;
            originX = mouseX - worldX * newScale;
            originY = mouseY - worldY * newScale;
            scale = newScale;
            updateTransform();
        };

        container.onmousedown = (e) => {
            if (e.button === 2 || e.button === 1) {
                isPanning = true;
                startPan = {x: e.clientX - originX, y: e.clientY - originY};
            }
        };

        document.addEventListener('mousemove', (e) => {
            if (isPanning) {
                originX = e.clientX - startPan.x;
                originY = e.clientY - startPan.y;
                updateTransform();
            } else if (draggedNode) {
                draggedNode.x = (e.clientX - originX) / scale - clickOffset.x;
                draggedNode.y = (e.clientY - originY) / scale - clickOffset.y;
                const el = document.getElementById('node-' + draggedNode.id);
                el.style.left = draggedNode.x + 'px';
                el.style.top = draggedNode.y + 'px';
                drawArrows();
            }
        });

        document.addEventListener('mouseup', () => {
            isPanning = false;
            if (draggedNode) {
                saveToLocalStorage();
                draggedNode = null;
            }
        });

        window.onresize = drawArrows;
    };

    function updateTransform() {
        $('#viewport').css('transform', 'translate(' + originX + 'px, ' + originY + 'px) scale(' + scale + ')');
        $('#zoom-label').text(Math.round(scale * 100) + '%');
        drawArrows();
    }

    function saveNode() {
        const id = parseInt($('#node-id').val());
        const text = $('#node-text').val();
        const type = $('#node-type').val();
        const options = [];
        if (type === 'common') {
            $('.opt-row').each(function (idx) {
                const txt = $(this).find('.o-txt').val();
                const next = $(this).find('.o-next').val();
                if (txt && next) options.push({id: idx, text: txt, nextNodeId: parseInt(next)});
            });
        }
        if (!text) return alert("Введите текст этапа!");
        const index = questNodes.findIndex(n => n.id === id);
        let x, y;
        if (index > -1) {
            x = questNodes[index].x;
            y = questNodes[index].y;
        } else {
            if (questNodes.length > 0) {
                const lastNode = questNodes.reduce((p, c) => (p.id > c.id) ? p : c);
                x = lastNode.x + 340;
                y = lastNode.y;
            } else {
                const container = document.getElementById('canvas-container');
                const rect = container.getBoundingClientRect();
                x = (rect.width / 2 - originX) / scale - 150;
                y = (rect.height / 2 - originY) / scale - 50;
            }
        }
        const nodeData = {id, text, type, options, x, y};
        if (index > -1) questNodes[index] = nodeData; else questNodes.push(nodeData);
        render();
        saveToLocalStorage();
        if (index === -1) $('#node-id').val(id + 1);
    }

    function drawArrows() {
        const canvas = document.getElementById('canvas-arrows');
        const ctx = canvas.getContext('2d');
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        questNodes.forEach(node => {
            if (node.type !== 'common') return;
            node.options.forEach(opt => {
                const endNode = questNodes.find(n => n.id === opt.nextNodeId);
                const optEl = document.getElementById('opt-p-' + node.id + '-' + opt.id);
                if (endNode && optEl) {
                    const sX = originX + (node.x + 300) * scale;
                    const sY = originY + (node.y + optEl.offsetTop + (optEl.offsetHeight / 2)) * scale;
                    const eX = originX + (endNode.x) * scale;
                    const eY = originY + (endNode.y + 35) * scale;
                    ctx.beginPath();
                    ctx.strokeStyle = "#ffd54f";
                    ctx.lineWidth = 2 * scale;
                    ctx.moveTo(sX, sY);
                    const cp1x = sX + (eX - sX) / 2;
                    ctx.bezierCurveTo(cp1x, sY, cp1x, eY, eX, eY);
                    ctx.stroke();
                }
            });
        });
    }

    function render() {
        const list = $('#nodes-list').empty();

        questNodes.forEach(node => {
            let badgeText = node.type === 'common' ? 'Этап' : (node.type === 'victory' ? 'Победа' : 'Проигрыш');

            // Формируем список опций
            let opts = node.options.map(o =>
				'<div class="option-preview" id="opt-p-' + node.id + '-' + o.id + '">'
                + '<b>"' + o.text + '"</b> ➔ #' + o.nextNodeId
				+ '</div>'
            ).join('');

            // Создаем карточку
            const background = node.type === 'victory' ? '#2e7d32' : (node.type === 'defeat' ? '#c62828' : '#1565c0');
            const card = $(
                '<div class="node-card ' + node.type + '" id="node-' + node.id + '" style="left:' + node.x + 'px; top:' + node.y + 'px;">'
                + '<button class="card-delete" onclick="deleteNode(' + node.id + ', event)">×</button>'
                + '<div style="font-size:0.7rem; font-weight:bold; padding:3px 8px; border-radius:4px; text-transform:uppercase; margin-bottom:10px; display:inline-block; background:' + background + '">'
                + badgeText + '#' + node.id
                + '</div>'
                + '<div class="node-text-display">' + node.text + '</div>'
                + opts
                + '</div>'
            ).on('mousedown', (e) => onNodeDown(e, node.id));
            list.append(card);
        });

        // Перерисовываем стрелки после рендера карточек
        setTimeout(drawArrows, 20);
    }

    function onNodeDown(e, id) {
        if ($(e.target).hasClass('card-delete')) return;
        e.stopPropagation();
        draggedNode = questNodes.find(n => n.id === id);
        clickOffset.x = (e.clientX - originX) / scale - draggedNode.x;
        clickOffset.y = (e.clientY - originY) / scale - draggedNode.y;
        editNode(id);
    }

    function addOptionRow(text = "", next = "") {
        $('#options-inputs').append(
            '<div class="opt-row">'
                + '<input type="text" class="o-txt" placeholder="Действие" style="flex:1;" value="' + text + '">'
                + '<input type="number" class="o-next" placeholder="ID" value="' + next + '">'
                + '<button onclick="$(this).parent().remove()" style="background:#c62828; color:white; border:none; border-radius:4px; width:30px; cursor:pointer;">×</button>'
            + '</div>'
        );
    }

    function editNode(id) {
        const node = questNodes.find(n => n.id === id);
        if (!node) return;
        $('#node-id').val(node.id);
        $('#node-text').val(node.text);
        $('#node-type').val(node.type || 'common').trigger('change');
        $('#options-inputs').html('<label>Выборы игрока</label>');
        node.options.forEach(o => addOptionRow(o.text, o.nextNodeId));
    }

    function toggleOptionsVisibility() {
        $('#options-section').toggle($('#node-type').val() === 'common');
    }

    function publishQuest() {
        const cleanNodes = questNodes.map(node => {
            const {x, y, ...clean} = node;
            return clean;
        });
        const data = {
            title: $('#q-title').val(),
            prologue: $('#q-prologue').val(),
            nodes: cleanNodes
        };
        $.ajax({
            url: '/editor', // Замените на ваш реальный URL
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function() {
                window.location.href = '/main-menu';
            },
            error: function(xhr, status, error) {
                alert("Ошибка при публикации: " + error);
                console.error("Статус:", status, "Детали:", xhr.responseText);
            }
        });
    }

    function saveToLocalStorage() {
        localStorage.setItem('quest', JSON.stringify({
            title: $('#q-title').val(),
            prologue: $('#q-prologue').val(),
            nodes: questNodes,
            cam: {x: originX, y: originY, s: scale}
        }));
    }

    function loadFromLocalStorage() {
        const saved = localStorage.getItem('quest');
        if (saved) {
            const d = JSON.parse(saved);
            $('#q-title').val(d.title);
            $('#q-prologue').val(d.prologue);
            questNodes = d.nodes || [];
            if (d.cam) {
                originX = d.cam.x;
                originY = d.cam.y;
                scale = d.cam.s;
            }
            render();
            updateTransform();
        }
    }

    function deleteNode(id, e) {
        e.stopPropagation();
        if (confirm('Удалить этап #' + id + '?')) {
            questNodes = questNodes.filter(n => n.id !== id);
            render();
            saveToLocalStorage();
        }
    }

    function clearAllData() {
        if (confirm("Удалить всё?")) {
            localStorage.clear();
            location.reload();
        }
    }
</script>
</body>
</html>