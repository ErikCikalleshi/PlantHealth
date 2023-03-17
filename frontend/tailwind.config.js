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
      },
      fontFamily: {
        'primary': ['Stolzl'],
      },
    },
  },
  plugins: [],
}
