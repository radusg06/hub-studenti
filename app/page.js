import StarsBackground from "@/components/StarsBackground/StarsBackground";
import Navbar from "@/components/Navbar/Navbar";
import Hero from "@/components/Hero/Hero";
import Features from "@/components/Features/Features";
import CTA from "@/components/CTA/CTA";
import Footer from "@/components/Footer/Footer";

export default function Home() {
  return (
    <>
      <StarsBackground />

      <div className="glow-wrap">
        <Navbar />
        <Hero />
      </div>

      <Features />
      <CTA />
      <Footer />
    </>
  );
}
