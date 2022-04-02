<template>
    <v-snackbar
      v-model="visible"
      :timeout="timeout"
      top: true
    >
      {{ text }}

      <template v-slot:action="{ attrs }">
        <v-btn
          color="blue"
          text
          v-bind="attrs"
          @click="visible = false"
        >
          关闭
        </v-btn>
      </template>
    </v-snackbar>
</template>

<script>
export default {
    props: {
        text: String,
        timeout: String,
        value: Boolean
    },
    data() {
        return { 
            visible: false
        }
    },
    watch: {
      value(val) {
        this.visible = val;
      },
      visible(val) {
        this.$emit('input', val);
      }
    }
}
</script>

<style>

</style>