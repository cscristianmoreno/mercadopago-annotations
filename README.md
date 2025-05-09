# Anotaciones para Checkout Pro

## Acerca de esto.

Permite una integración más sencilla de <b>Checkout Pro</b> de <b>Mercado pago</b>. Esta librería brinda lo siguiente 

* Personalización de cuotas
* Excluir tipos de pago.
* Excluir métodos de pago. 
* URL de notificación.
* Duración de una preferencia.
* Controlador predefinido

## ¿Cómo funciona?

<p>Utiliza la <b>SDK de Mercado Pago</b> para simplificar las cosas a través de anotaciones mediante <b>reflexión</b>.</p>


## ¿Cómo inicializarlo?
Para inicializarlo es necesario la anotación <b>@MercadoPago</b> e inicializarlo en la clase <b>Main</b>.

```java
@MercadoPago
public class PruebaApplication {

	public static void main(String[] args) throws Exception {
		MP.initializer(PruebaApplication.class);
	}
}
```

## Controlador integrado
<p>
    Para usar el controlador integrado, es necesario crear una clase con la anotación <b>@MercadoPagoController</b> para ser detectada automáticamente.
</p>

_<p>NOTA: Es posible que las solicitudes no sean enviadas en tiempo real a los métodos.</p>_

<!-- <p> -->
* <b>@MPPaymentPending</b> 
    - El usuario aún no ha completado el proceso de pago
* <b>@MPPaymentResponse</b> 
    - Escucha todas las respuestas
* <b>@MPPaymentApproved</b> 
    - El pago ha sido aprobado y acreditado con éxito.
* <b>@MPPaymentAuthorized</b> 
    - El pago ha sido autorizado pero aún no se ha capturado.
* <b>@MPPaymentInProccess</b> 
    - El pago está en proceso de revisión.
* <b>@MPPaymentInMediation</b> 
    - El usuario ha iniciado una disputa.
* <b>@MPPaymentRejected</b> 
    - El pago fue rechazado.
* <b>@MPPaymentCancelled</b> 
    - El pago fue cancelado por alguna de las partes o caducó.
* <b>@MPPaymentRefunded</b> 
    - El pago fue reembolsado al usuario.
* <b>@MPPaymentChargedBack</b> 
    - Se realizó un contracargo en la tarjeta de crédito del comprador.

```java
@MercadoPagoController
public class MercadoPagoController {

    @MPPaymentResponse
    public void getResponse(MPResponseBody body) {
    }

    @MPPaymentPending
    public void getPending(MPResponseBody body) {
    }

    @MPPaymentApproved
    public void getSuccess(MPResponseBody body) {
    }

    @MPPaymentRejected
    public void getReject(MPResponseBody body) {
    }
}
```

## Spring Boot

<p>Por defecto <b>Spring Boot</b> utiliza su propio contenedor de <b>Servlets</b> y <b>ServletContainerInitializer</b> no puede ser instanciado automáticamente.</p>

<p>Para ello, este tiene que ser añadido manualmente a través de <b>ServletContextInitializer</b>.</p>

```java
@Component
public class MyServletContextInitializer implements ServletContextInitializer {
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        MPServletInitializer servletInitializer = new MPServletInitializer();
        servletInitializer.onStartup(MPControllerUtil.getControllers(), servletContext);
    }
}
```

#
<p>Para obtener una respuesta de <b>Mercado Pago</b> en un controlador de tipo <b>@Controller</b>, se puede utilizar la clase <b>MPResult</b> como parámetro de <b>@RequestBody</b> y obtener el payment a través de <b>new PaymentClient()</b>.</p>

```java
@PostMapping("/payment/success")
public void postMethodName(@RequestBody MPResult result) {
    String id = result.getData().getId();
    PaymentClient paymentClient = new PaymentClient();
    Payment payment = paymentClient.get(id);
}
```

## Registrar productos
<p>Para registrar un produto, los atributos <b>@UnitPrice</b> y <b>@Quantity</b> son obligatorios.</p>

```java
@MercadoPagoITem
public class newProduct {
    @Id
    private String id;
    
    @Title
    private String title = "Título de producto";

    @Description
    private String description = "Descripción de producto";

    @UnitPrice
    private int price = 100;

    @PictureUrl
    private String image = "...";

    @CurrencyId
    private String currency = "ARS";

    @Quantity
    private int quantity = 1;

    @CategoryId
    private String categoryId = "asd";
}
```

_Po defecto el campo @Id es autoincrementable al ser nulo._

## Obtener productos 
<p>Para obtener ya sea un producto registrado, o todos, se debe utilizar la clase <b>MPItem</b>. Esto devuelve una lista de <b>PreferenceItemRequest</b></p>.

```java
List<PreferenceItemRequest> items = MPItem.getItems();
```

```java
Optional<PreferenceItemRequest> item = MPItem.getItem(String id);
```

## Crear preferencia

<p>Para crear una preferencia se necesitan los productos e información del pagador, este último es opcional.</p>

_NOTA: Es recomendable enviar sólamente los identificadores de los productos y crear una lista con los objetos encontrados._

```java
List<PreferenceItemRequest> items = ...;
MPPreference preference = new MPPreference();
Preference preference = preference.createPreference(items);

preference.getInitPoint();
```

Esto devolverá

```java
https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=1631883986-XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

## @MercadoPago

<p>Esta anotación ofrece las configuraciones necesarias para tu aplicación. Por defecto, las variables con <b>$</b> aluden a variables de entorno. Por ejemplo:</p>

```java
@MercadoPago(
    # Leerá la variable de entorno MP_ACCESS_TOKEN
    accessToken="$MP_ACCESS_TOKEN",
    # Asignará un valor fijo
    accessToken="AR-2-10D-S02-S0"
)
```

```code
# Token de acceso de Mercado Pago.
String accessToken()

# URL de redireccionamiento en caso de exito.
String redirectSuccessUrl()

# URL de redireccionamiento en caso de rechazo.
String redirectFailureUrl()

# URL de redireccionamiento en caso de pendiente.
String redirectPendingUrl()

# URL en el cuál se enviarán las notificaciones.
String notificationUrl()

# Clave secreta (Opcional).
String secretKey()

# Cantidad de cuotas permitidas.
int installments()

# Excluir métodos de pago. Por ejemplo: Rapipago.
PaymentMethod[] excludedPaymentMethod()

# Excluir tipos de pago. Por ejemplo: Efectivo.
PaymentType[] excludedPaymentType()

# Duración de un enlace vigente para ser pagado. Por ejemplo: 3 horas.
PreferenceDuration preferenceDuration()

# Habilita si un enlace será expirado.
boolean expires()

# Paquete a escanear. Por defecto escanea en "com"
String packageScan()
```

## @MercadoPagoServer

<p>Esta anotación ofrece un servidor de tomcat embebido para que tu aplicación sea levantada.</p>

```code
# Puerto en el cuál se levantará el servidor.
int port()
```