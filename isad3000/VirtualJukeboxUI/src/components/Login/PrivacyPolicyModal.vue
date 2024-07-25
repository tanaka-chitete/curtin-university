<script lang="ts">
/* 
This is a modal which will appear when a user has clicked to view the terms and
conditions
*/
import { defineComponent } from "vue";
/* Stores */
export default defineComponent({
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const PrivacyPolicyModal = document.getElementById("privacypolicy");
      if (PrivacyPolicyModal) {
        PrivacyPolicyModal.style.display = "none";
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "privacypolicy") {
        this.close();
      }
    },
  },
});
</script>

<template>
  <div id="privacypolicy" class="modal" @click="clickOutside($event)">
    <div class="modal-content">
      <div class="top-bar">
        <span class="close" @click="close()">&times;</span>
        <div class="modal-title">Privacy Policy</div>
      </div>
      <div class="modal-text">
        Amristar operates the labs2.amristar.com domain, which hosts Virtual
        Jukebox (our Service).
        <br />
        <br />
        This pop-up is used to inform users regarding the Privacy Policy of our
        Service, Virtual Jukebox. If you choose to use our Service, then you
        agree to the collection and use of information in relation with this
        policy. The Personal Information that we collect are used for providing
        and improving the Service. We will not use or share your information
        with anyone except as described in this Privacy Policy.
        <br />
        <br />
        <div class="modal-sub-title">Information Collection and Use</div>
        For a better experience while using our Service, we may require you to
        provide us with certain personally identifiable information, namely,
        your geolocation. This information we collect will be used to facilitate
        the creation and joining of sessions.
        <br />
        <br />
        <div class="modal-sub-title">Log Data</div>
        We want to inform you that whenever you visit our Service, we collect
        information that your browser sends to us that is called Log Data. This
        Log Data may include information such as your computer's Internet
        Protocol (IP) address, browser version, pages of our Service that you
        visit, the time and date of your visit, the time spent on those pages,
        and other statistics.
        <br />
        <br />
        <div class="modal-sub-title">Cookies</div>
        Cookies are files with small amount of data that is commonly used an
        anonymous unique identifier. These are sent to your browser from the
        website that you visit and are stored on your computer's hard drive. Our
        website uses these "cookies" to collection information and to improve
        our Service. You have the option to either accept or refuse these
        cookies, and know when a cookie is being sent to your computer. If you
        choose to refuse our cookies, you will not be able to use our service as
        intended.
        <br />
        <br />
        <div class="modal-sub-title">Service Providers</div>
        We employ third-party companies due to the following reasons:
        <ul>
          <li>To facilitate our Service;</li>
          <li>To perform Service-related services; or</li>
          <li>To assist us in analysing how our Service is used.</li>
        </ul>
        We want to users that these third parties may have access to your
        Personal Information. The reason is to perform the tasks assigned to
        them on our behalf. However, they are obligated not to disclose or use
        the information for any other purpose.
        <br />
        <br />
        <div class="modal-sub-title">Security</div>
        We value your trust in providing us your information, thus we make use
        of many best-practices to ensure its security. However, remember that no
        method of transmission over the internet, or method of electronic
        storage is 100% secure and reliable, and we cannot guarantee its
        absolute security.
        <br />
        <br />
        <div class="modal-sub-title">Links to Other Sites</div>
        Our Service contains links to other sites. If you click on a third-party
        link, you will be directed to that site. Note that these external sites
        are not operated by us. Therefore, we strongly advise you to review the
        Privacy Policy of these websites. We have no control over, and assume no
        responsibility for the content, privacy policies, or practices of any
        third-party sites or services.
        <br />
        <br />
        <div class="modal-sub-title">Changes to the Privacy Policy</div>
        We may update our Privacy Policy from time to time. Thus, we advise you
        to review this page periodically for any changes. We will notify you of
        any changes by posting the new Privacy Policy on this page. These
        changes are effective immediately, after they are posted on this page.
        <br />
        <br />
        Our Privacy Policy was created with the help of the
        <a href="https://www.privacypolicytemplate.net/" _target="blank"
          >Privacy Policy Template Generator</a
        >.
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "../../assets/modal.scss";
@import "../../assets/_button.scss";
@import "../../assets/_typography.scss";
@import "../../assets/_container.scss";
.modal {
  @include modal;
}

/* Modal Content/Box */
.modal-content {
  @include modal-content;
  margin: 10% auto; /* 15% from the top and centered */
  overflow-x: hidden; // Prevent connect button from popping out of div when using float:right
  height: 65vh;
  overflow-y: scroll;
}

.modal-title {
  @include text-content-bold;
  height: 3em; // Spacing after title
  width: 50%; // Prevent it from overlapping the close button, preventing it from being clicked
}

.modal-sub-title {
  @include text-small-bold;
  height: 2em; // Spacing after sub-title
}

.modal-text {
  @include text-small;
  height: 5em; // Spacing after text
}

/* The Close Button */
.close {
  @include text-content;
  color: #00bd7e;
  float: right;
  background: none;
  border: none;
}

.close:hover,
.close:focus {
  color: #ffffff;
  text-decoration: none;
  cursor: pointer;
}

// For mobile view
@media only screen and (max-device-width: 500px) {
  .modal-content {
    width: 90vw;
    position: relative;
    top: 10vw;
    height: 75vh;
  }

  .modal-title {
    @include text-mobile-large;
    width: 90vw;
  }
  .modal-text {
    @include text-mobile-small;
  }

  .modal-sub-title {
    @include text-mobile-large;
    width: 90vw;
  }
  .close {
    @include modal-mobile-close;
  }
  .top-bar {
    @include modal-mobile-top-bar;
  }
}
</style>
