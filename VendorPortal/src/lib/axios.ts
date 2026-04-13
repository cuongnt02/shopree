import axios from 'axios'

export const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080'
})

api.interceptors.request.use((config) => {
    const raw = localStorage.getItem('vendor_auth');
    const token = raw ? JSON.parse(raw).accessToken : null
    if (token) config.headers.Authorization = `Bearer ${token}`
    return config
})

api.interceptors.response.use(
    (res) => res,
    (error) => {
        if (error.response?.status === 401) {
            localStorage.removeItem('vendor_auth')
            window.location.href = '/login'
        }
        return Promise.reject(error)
    }
)