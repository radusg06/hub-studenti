"use client";

import { useEffect, useRef } from "react";

export default function StarsBackground() {
  const starsRef = useRef(null);

  useEffect(() => {
    const container = starsRef.current;
    if (!container) return;

    const prefersReduced = window.matchMedia(
      "(prefers-reduced-motion: reduce)"
    ).matches;
    const count = window.innerWidth < 600 ? 55 : 110;
    const frag = document.createDocumentFragment();

    for (let i = 0; i < count; i++) {
      const star = document.createElement("div");
      const size = Math.random() * 2 + 1;
      const big = size > 2.3;
      star.className =
        "star" + (big ? " big" : "") + (Math.random() < 0.15 ? " tinted" : "");
      star.style.top = Math.random() * 100 + "%";
      star.style.left = Math.random() * 100 + "%";
      star.style.width = size + "px";
      star.style.height = size + "px";
      if (!prefersReduced) {
        star.style.animationDuration = 3 + Math.random() * 4 + "s";
        star.style.animationDelay = Math.random() * 5 + "s";
      }
      frag.appendChild(star);
    }
    container.appendChild(frag);

    return () => {
      container.innerHTML = "";
    };
  }, []);

  return (
    <div id="stars" ref={starsRef} aria-hidden="true">
      <div className="shooting-star"></div>
    </div>
  );
}
