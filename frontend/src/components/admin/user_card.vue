<template>
  <v-card class="overflow-hidden shadow-xl border border-primary pa-3 font-primary">
    <div class="flex gap-3 pb-3">
      <img alt="PlaceholderUserProfilePicture" class="block rounded-full bg w-[100px] h-[100px]"
           src="https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg">
      <header class="flex flex-col gap-2 w-full">
        <div>
          <h3 class="no-underline text-black select-none font-semibold">
            {{ user.firstname }} {{ user.lastname }}
          </h3>
          <p class="text-xs text-gray-500">{{ user.created }}</p>
        </div>
        <div class="flex flex-wrap gap-1 bg-gray-100 p-2 rounded">
          <p class="border-[1.5px] rounded-full px-2 text-xs"
             :class="'border-'+getColorByRole(role) + ' text-' +getColorByRole(role)"
             v-for="role in user.roles.sort()">
            {{ role }}
          </p>
        </div>
        <div>
          <div class="flex align-center gap-2">
            <v-icon icon="mdi-account" size="24px"></v-icon>
            <p class="text-xs text-gray-500">{{ user.username }}</p>
          </div>
          <div class="flex align-center gap-2">
            <v-icon icon="mdi-email-outline" size="24px"></v-icon>
            <p class="text-xs text-gray-500">{{ truncateString(user.email, 20) }}</p>
          </div>
        </div>
      </header>
    </div>
    <div class="grid grid-cols-2 gap-2 items-end">
      <v-btn variant="flat" color="primary"
             @click="$router.push({name:'admin-edit-user', params:{username: user.username}})">
        Edit User
      </v-btn>
      <v-btn variant="flat" color="error">
        Delete User

        <v-dialog
            v-model="deleteDialog"
            activator="parent"
            width="auto"
        >
          <v-card class="font-primary">
            <v-card-title>
              Confirm Delete
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
              Are you sure you want to delete this user? This cannot be undone.
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  color="primary"
                  variant="tonal"
                  @click="deleteDialog = false;"
              >
                Close
              </v-btn>
              <v-btn
                  color="error"
                  variant="tonal"
                  @click="emitConfirm"
              >
                Delete
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-btn>
    </div>
  </v-card>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import type IUser from "@/interfaces/user/IUser";
export default defineComponent({
  data() {
    return {
      deleteUserModalVisible: false,
      deleteDialog: false,
      userToDelete: null as unknown as IUser,
    }
  },
  props: {
    user: {
      type: Object as () => IUser,
      required: true
    },
  },
  methods: {
    getColorByRole(role: String) {
      switch (role) {
        case "GARDENER":
          return "role-gardener"
        case "ADMIN":
          return "role-admin"
        default:
          return "role-user"
      }
    },
    truncateString(str: string, num: number) {
      if (str.length > num) {
        return str.slice(0, num) + "...";
      } else {
        return str;
      }
    }
  },
  emits: ['confirm'],
  setup(props, { emit }) {
    const emitConfirm = () => {
      emit('confirm');
    };
    return { emitConfirm };
  },
});
</script>