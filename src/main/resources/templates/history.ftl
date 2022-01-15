<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width">

  <title>Converter</title>
    <script src="/js/jquery-3.6.0.min.js"></script>
  <link rel="stylesheet" type="text/css" href="/css/history.css"/>
</head>

<body>
    <form action="/history" method="get">
        <div class="columns">
            <div class="field">
                <input type="date" id="date" name="date"/>
            </div>
            <div class="field">
                <select id="source" name="source">
                    <#list currency as item>
                        <option value="${item.id}">${item.char_code} (${item.name})</option>
                    </#list>
                </select>
            </div>
            <div class="field">
                <select id="target" name="target">
                    <#list currency as item>
                        <option value="${item.id}">${item.char_code} (${item.name})</option>
                    </#list>
                </select>
            </div>
            <div class="field">
                <input type="submit" value="Поиск"/>
                <input type="button"  onClick="window.location='/history'" value="Очистить"/>
            </div>
        </div>
    </form>
    <table>
            <tr style="background: gray;">
                <th>Исходная валюта</th>
                <th>Целевая валюта</th>
                <th>Исходная сумма</th>
                <th>Получаемая сумма</th>
                <th>Дата</th>
            </tr>
            <#list history as item>
                <tr style="background: lightgray;">
                    <th>${item.source}</th>
                    <th>${item.target}</th>
                    <th>${item.source_value}</th>
                    <th>${item.target_value}</th>
                    <th>${item.rate_date}</th>
                </tr>
            </#list>
        </table>
    <script src="/js/history.js"></script>
</body>
</html>