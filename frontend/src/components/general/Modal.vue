<template>
    <transition name="modal">
        <div v-if="isVisible">
            <div class="fixed inset-0 z-50 flex justify-center items-center">
                <div class="flex flex-col max-w-5xl rounded-lg shadow-lg bg-white">
                    <!-- header -->
                    <div class="p-5">
                        <div class="flex justify-between items-start">
                            <h3 class="text-2xl font-semibold">
                                <slot name="title"></slot>
                            </h3>
                            <button class="p-1 leading-none" @click="emitCancel">
                                <div class="text-xl font-semibold h-6 w-6">
                                    <span>x</span>
                                </div>
                            </button>
                        </div>
                    </div>
                    <!-- body -->
                    <div class="p-6">
                        <slot name="body"></slot>
                    </div>
                    <!-- footer -->
                    <slot name="footer">
                        <div class="p-6 flex justify-end items-center">
                            <button @click="emitCancel">
                                <slot name="cancel-button">
                                    <button class="btn-outline">Cancel</button>
                                </slot>
                            </button>
                            <button class="ml-2" @click="emitConfirm">
                                <slot name="confirm-button">
                                    <button class="btn">Confirm</button>
                                </slot>
                            </button>
                        </div>
                    </slot>
                </div>
            </div>
            <div class="opacity-25 fixed inset-0 z-40 bg-black"></div>
        </div>
    </transition>
</template>

<script lang='ts'>
import { defineComponent, ref } from 'vue';

export default defineComponent({
    name: 'Modal',
    props: {
        isVisible: {
            type: Boolean,
            required: true,
        },
    },
    emits: ['cancel', 'confirm'],
    setup(props, { emit }) {
        const emitCancel = () => {
            emit('cancel');
        };

        const emitConfirm = () => {
            emit('confirm');
        };

        return { emitCancel, emitConfirm };
    },
});
</script>

<style scoped>
.modal-leave-active, .modal-enter-active {
    @apply duration-200;
    @apply transition;
}

.modal-enter, .modal-leave-to {
    @apply opacity-0;
}
</style>
