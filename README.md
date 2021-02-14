# XXICentury-Task
Запускается через порт 9999  </br>
Формирование "Строки заказа" происходит: (POST) localhost:9999/order_line/?order_id=1&product_id=1&count=1 </br>
Также можно сформиравать заказ: (POST) localhost:9999/order/?product_id=1&count=1 (и в тело запроса еще передать Order(имя клиента, дата, адрес))</br>
