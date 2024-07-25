import { defineStore } from "pinia";
/* This file stoes a user object on local storage 
User object has username and id fields, both strings */

export const useUser = defineStore({
  id: "user",
  state: () => ({
    username: "",
    id: "",
    latitude: "",
    longitude: "",
  }),
  actions: {
    setUsername(newName: string) {
      this.username = newName;
    },
    setID(newID: string) {
      this.id = newID;
    },
    getLocation(position: any) {
      // Set the latitude and longitude
      this.latitude = position.coords.latitude;
      this.longitude = position.coords.longitude;
    },
    findLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(this.getLocation);
        return true;
      } else {
        return false;
      }
    },
    hasLocation() {
      if (this.latitude && this.longitude) return true;
      return false;
    },
    clearUserInfo() {
      this.id = "";
      this.username = "";
    },
  },
  persist: true,
});
