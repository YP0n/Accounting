import moment from 'moment'

export const getFormattedDate = (date, format = 'DD/MM/YYYY') => {
  return date && moment(date).format(format)
}