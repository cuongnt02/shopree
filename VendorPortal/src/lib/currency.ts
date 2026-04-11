export function formatVnd(cents: number): string {
    return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(cents)
}