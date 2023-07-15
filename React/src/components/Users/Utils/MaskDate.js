
export default function MaskDate(date) {
   return date.toLocaleString().split('T')[0].split('-').reverse().join('/')
}
