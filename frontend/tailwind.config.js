/** @type {import('tailwindcss').Config} */
module.exports = {
  important: true,
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'primary': '#449A8B',
        'plant-health-red': '#9A0606',
      },
      fontFamily: {
        'primary': ['Stolzl'],
        'secondary': ['Stolzl-Light'],
        'roboto':['Roboto'],
      },
    },
  },
  plugins: [],
}
