import axios from 'axios'

export const getExpenses = async params => {
  try {
    const { data: responseData } = await axios.get('/personal_expenses/api/expenses', { params })

    return responseData
  } catch(e) {
    throw e
  }
}

export const addExpense = async data => {
  try {

    await axios.post('/personal_expenses/api/expenses', data)
  } catch (e) {
    throw e
  }
}

export const updateExpense = async data => {
  try {
    const { data: responseData } = await axios.put('/personal_expenses/api/expenses', data)

    return responseData
  } catch(e) {
    throw e
  }
}

export const removeExpense = async expenseId => {
  try {
    const { data } = await axios.delete('/personal_expenses/api/expenses', expenseId)

    return data
  } catch (e) {
    throw e
  }
}