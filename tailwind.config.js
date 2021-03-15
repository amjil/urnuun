const plugin = require('tailwindcss/plugin')

module.exports = {
  purge: [
    './src/**/*.cljs',
  ],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {},
  },
  variants: {
    extend: {},
  },
  plugins: [
    plugin(function({ addUtilities }) {
      const newUtilities = {
        '.mode-lr': {
          'writing-mode': 'vertical-lr'
        },
        '.mode-tb': {
          'writing-mode': 'horizontal-tb'
        },
        '.mgl': {
          'font-family': 'Mongolian White',
          'text-orientation': 'sideways-right'
        }, 
      }

      addUtilities(newUtilities, ['responsive', 'hover'])
    })
  ],
}
