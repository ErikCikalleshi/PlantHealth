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
                'role-user': '#726F6F',
                'role-gardener': '#1D5F1C',
                'role-admin': '#0454B2',
            },
            fontFamily: {
                'primary':
                    ['Stolzl'],
                'secondary':
                    ['Stolzl-Light'],
                'roboto':
                    ['Roboto'],
            }
            ,
        },
    },
    plugins: [],
    safelist:[
        'border-role-user',
        'border-role-gardener',
        'border-role-admin',
        'text-role-user',
        'text-role-gardener',
        'text-role-admin',
    ]
}
