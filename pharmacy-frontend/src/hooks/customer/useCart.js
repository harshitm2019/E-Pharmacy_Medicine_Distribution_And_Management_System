import { useQuery, useQueryClient } from "@tanstack/react-query";

const CART_KEY = ["cart"];
const ONE_DAY = 24 * 60 * 60 * 1000;

function useCart() {
    const queryClient = useQueryClient();

    const { data: cart = [] } = useQuery({
        queryKey: CART_KEY,
        queryFn: async () => [],
        initialData: [],
        staleTime: ONE_DAY,
        gcTime: ONE_DAY
    });

    function addToCart(medicine, quantity = 1) {
        queryClient.setQueryData(CART_KEY, currentCart => {
            const existingItem = currentCart.find(
                item => item.medicineId === medicine.medicineId
            );

            if (existingItem) {
                return currentCart.map(item =>
                    item.medicineId === medicine.medicineId
                        ? {
                              ...item,
                              quantity: Math.min(
                                  item.quantity + quantity,
                                  item.stockQuantity
                              )
                          }
                        : item
                );
            }

            return [
                ...currentCart,
                {
                    medicineId: medicine.medicineId,
                    medicineName: medicine.medicineName,
                    categoryId: medicine.categoryId,
                    categoryName: medicine.categoryName,
                    manufacturer: medicine.manufacturer,
                    price: medicine.price,
                    discount: medicine.discount,
                    sellingPrice: medicine.sellingPrice,
                    stockQuantity: medicine.stockQuantity,
                    prescriptionNeed: medicine.prescriptionNeed,
                    medicineImage: medicine.medicineImage,
                    quantity
                }
            ];
        });
    }

    function updateQuantity(medicineId, quantity) {
        queryClient.setQueryData(CART_KEY, currentCart =>
            currentCart.map(item =>
                item.medicineId === medicineId
                    ? {
                          ...item,
                          quantity: Math.max(
                              1,
                              Math.min(
                                  quantity,
                                  item.stockQuantity
                              )
                          )
                      }
                    : item
            )
        );
    }

    function increaseQuantity(medicineId) {
        queryClient.setQueryData(CART_KEY, currentCart =>
            currentCart.map(item =>
                item.medicineId === medicineId
                    ? {
                          ...item,
                          quantity: Math.min(
                              item.quantity + 1,
                              item.stockQuantity
                          )
                      }
                    : item
            )
        );
    }

    function decreaseQuantity(medicineId) {
        queryClient.setQueryData(CART_KEY, currentCart =>
            currentCart
                .map(item =>
                    item.medicineId === medicineId
                        ? {
                              ...item,
                              quantity: item.quantity - 1
                          }
                        : item
                )
                .filter(item => item.quantity > 0)
        );
    }

    function removeFromCart(medicineId) {
        queryClient.setQueryData(CART_KEY, currentCart =>
            currentCart.filter(
                item => item.medicineId !== medicineId
            )
        );
    }

    function clearCart() {
        queryClient.setQueryData(CART_KEY, []);
    }

    const totalItems = cart.reduce(
        (total, item) => total + item.quantity,
        0
    );

    const totalAmount = cart.reduce(
        (total, item) =>
            total +
            Number(item.sellingPrice) * item.quantity,
        0
    );

    const hasPrescriptionMedicine = cart.some(
        item =>
            item.prescriptionNeed === "Yes" ||
            item.prescriptionNeed === "YES"
    );

    return {
        cart,
        totalItems,
        totalAmount,
        hasPrescriptionMedicine,
        addToCart,
        updateQuantity,
        increaseQuantity,
        decreaseQuantity,
        removeFromCart,
        clearCart
    };
}
export default useCart;