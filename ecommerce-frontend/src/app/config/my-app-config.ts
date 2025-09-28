export default {
  auth: {
    domain: "<<UPDATE-WITH-YOUR-DEV-DOMAIN>>",
    clientId: "<<UPDATE-WITH-YOUR-APP-CLIENT-ID>>",
    authorizationParams: {
      redirect_uri: "http://localhost:4200",
      audience: "http://localhost:8080",
    },
  },
  httpInterceptor: {
    allowedList: [
      'http://localhost:8080/api/orders/**',
      'http://localhost:8080/api/checkout/purchase'
    ],
  },
}