<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width">

  <title>Converter</title>
  <link rel="stylesheet" type="text/css" href="/css/converter.css">
    <script src="/js/jquery-3.6.0.min.js"></script>
</head>

<body>
    <div class="converter">
        <p id="error">Ошибка</p>
        <div class="column">
            <div class="field">
                <select id="source">
                    <#list currency as item>
                        <option value="${item.id}">${item.char_code} (${item.name})</option>
                    </#list>
                </select>
                <input id="source_input" placeholder="0"/>
            </div>
            <div class="field">
                <select id="target">
                    <#list currency as item>
                        <option value="${item.id}">${item.char_code} (${item.name})</option>
                    </#list>
                </select>
                <input id="target_input" placeholder="0" disabled="true"/>
            </div>
        </div>
        <input type="button" id="submit_b" value="Конвертировать"/>
        <input type="button" id="history_b" value="История" onClick="window.location='/history'"/>
    </div>
    <script src="/js/converter.js"></script>
</body>
</html>