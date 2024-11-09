# Convert-Everything-currency-convertor-
# Currency Converter App

## Features
- **Currency Conversion**: Users can select two different countries and convert the amount from one currency to another in real-time.
- **Live Data**: Fetches the latest currency conversion rates via the Frankfurter API.
- **User-friendly Interface**: Two input fields for currency amounts and two dropdown menus for selecting countries.

## Prerequisites
- Android Studio (latest version recommended)
- Internet access to fetch data from the API
- Android SDK version 21 or higher

## API Integration
The app uses the Frankfurter API to get live currency exchange rates. The API endpoint used is:


\`\`\`bash
https://api.frankfurter.app/latest?amount={amount}&from={fromCurrency}&to={toCurrency}
\`\`\`

- **amount**: The amount of currency to convert.
- **fromCurrency**: The base currency code (e.g., USD).
- **toCurrency**: The target currency code (e.g., EUR).

## Usage

1. Open the app.
2. Select the base currency from the first dropdown (e.g., USD).
3. Select the target currency from the second dropdown (e.g., EUR).
4. Enter the amount you wish to convert in the first input field.
5. Press the \"Convert\" button to get the conversion result.
6. The converted amount will be displayed in the second input field.

## Libraries Used

- **Volley**: Used for making network requests to fetch data from the Frankfurter API.
- **AndroidX**: For modern Android app development using the AndroidX library components.
