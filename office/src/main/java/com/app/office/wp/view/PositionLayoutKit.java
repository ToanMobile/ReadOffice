package com.app.office.wp.view;

import com.app.office.common.shape.WPAutoShape;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.view.PageAttr;

public class PositionLayoutKit {
    private static PositionLayoutKit kit = new PositionLayoutKit();

    private PositionLayoutKit() {
    }

    public static PositionLayoutKit instance() {
        return kit;
    }

    public void processShapePosition(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        processHorizontalPosition(leafView, wPAutoShape, pageAttr);
        processVerticalPosition(leafView, wPAutoShape, pageAttr);
    }

    private void processHorizontalPosition(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        byte horPositionType = wPAutoShape.getHorPositionType();
        byte horizontalRelativeTo = wPAutoShape.getHorizontalRelativeTo();
        if (horPositionType == 1) {
            float horRelativeValue = ((float) wPAutoShape.getHorRelativeValue()) / 1000.0f;
            if (horizontalRelativeTo == 2) {
                leafView.setX(Math.round(((float) pageAttr.pageWidth) * horRelativeValue));
            } else if (horizontalRelativeTo == 1) {
                leafView.setX(pageAttr.leftMargin + Math.round(((float) ((pageAttr.pageWidth - pageAttr.leftMargin) - pageAttr.rightMargin)) * horRelativeValue));
            } else if (horizontalRelativeTo == 4) {
                leafView.setX(Math.round(((float) pageAttr.leftMargin) * horRelativeValue));
            } else if (horizontalRelativeTo == 5) {
                leafView.setX((pageAttr.pageWidth - pageAttr.rightMargin) + Math.round(((float) pageAttr.rightMargin) * horRelativeValue));
            } else if (horizontalRelativeTo == 9) {
                if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
                    if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                        leafView.setX((pageAttr.pageWidth - pageAttr.rightMargin) + Math.round(((float) pageAttr.rightMargin) * horRelativeValue));
                    } else {
                        leafView.setX(Math.round(((float) pageAttr.leftMargin) * horRelativeValue));
                    }
                }
            } else if (horizontalRelativeTo != 8) {
            } else {
                if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                    leafView.setX(Math.round(((float) pageAttr.leftMargin) * horRelativeValue));
                } else {
                    leafView.setX((pageAttr.pageWidth - pageAttr.rightMargin) + Math.round(((float) pageAttr.rightMargin) * horRelativeValue));
                }
            }
        } else {
            byte horizontalAlignment = wPAutoShape.getHorizontalAlignment();
            if (horizontalAlignment == 0) {
                processHorizontalPosition_Absolute(leafView, wPAutoShape, pageAttr);
            } else if (horizontalAlignment == 1) {
                processHorizontalPosition_Left(leafView, wPAutoShape, pageAttr);
            } else if (horizontalAlignment == 2) {
                processHorizontalPosition_Center(leafView, wPAutoShape, pageAttr);
            } else if (horizontalAlignment == 3) {
                processHorizontalPosition_Right(leafView, wPAutoShape, pageAttr);
            } else if (horizontalAlignment == 6) {
                processHorizontalPosition_Inside(leafView, wPAutoShape, pageAttr);
            } else if (horizontalAlignment == 7) {
                processHorizontalPosition_Outside(leafView, wPAutoShape, pageAttr);
            }
        }
    }

    private void processHorizontalPosition_Absolute(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        Rectangle bounds = wPAutoShape.getBounds();
        byte horizontalRelativeTo = wPAutoShape.getHorizontalRelativeTo();
        if (horizontalRelativeTo == 1 || horizontalRelativeTo == 10 || horizontalRelativeTo == 0 || horizontalRelativeTo == 3) {
            leafView.setX(pageAttr.leftMargin + bounds.x);
        } else if (horizontalRelativeTo == 2 || horizontalRelativeTo == 4) {
            leafView.setX(bounds.x);
        } else if (horizontalRelativeTo == 5) {
            leafView.setX((pageAttr.pageWidth - pageAttr.rightMargin) + bounds.x);
        } else if (horizontalRelativeTo == 9) {
            if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
                if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                    leafView.setX((pageAttr.pageWidth - pageAttr.rightMargin) + bounds.x);
                } else {
                    leafView.setX(bounds.x);
                }
            }
        } else if (horizontalRelativeTo == 8 && leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
            if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                leafView.setX(bounds.x);
            } else {
                leafView.setX((pageAttr.pageWidth - pageAttr.rightMargin) + bounds.x);
            }
        }
    }

    private void processHorizontalPosition_Left(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        wPAutoShape.getBounds();
        byte horizontalRelativeTo = wPAutoShape.getHorizontalRelativeTo();
        if (horizontalRelativeTo == 1 || horizontalRelativeTo == 10 || horizontalRelativeTo == 0 || horizontalRelativeTo == 3) {
            leafView.setX(pageAttr.leftMargin);
        } else if (horizontalRelativeTo == 2 || horizontalRelativeTo == 4) {
            leafView.setX(0);
        } else if (horizontalRelativeTo == 5) {
            leafView.setX(pageAttr.pageWidth - pageAttr.rightMargin);
        } else if (horizontalRelativeTo == 9) {
            if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
                if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                    leafView.setX(pageAttr.pageWidth - pageAttr.rightMargin);
                } else {
                    leafView.setX(0);
                }
            }
        } else if (horizontalRelativeTo == 8 && leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
            if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                leafView.setX(0);
            } else {
                leafView.setX(pageAttr.pageWidth - pageAttr.rightMargin);
            }
        }
    }

    private void processHorizontalPosition_Center(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        int i = wPAutoShape.getBounds().width / 2;
        byte horizontalRelativeTo = wPAutoShape.getHorizontalRelativeTo();
        if (horizontalRelativeTo == 2) {
            leafView.setX((pageAttr.pageWidth / 2) - i);
        } else if (horizontalRelativeTo == 1 || horizontalRelativeTo == 0) {
            leafView.setX((pageAttr.leftMargin + (((pageAttr.pageWidth - pageAttr.leftMargin) - pageAttr.rightMargin) / 2)) - i);
        } else if (horizontalRelativeTo == 3) {
            leafView.setX(pageAttr.leftMargin - i);
        } else if (horizontalRelativeTo == 4) {
            leafView.setX((pageAttr.leftMargin / 2) - i);
        } else if (horizontalRelativeTo == 5) {
            leafView.setX((pageAttr.pageWidth - (pageAttr.rightMargin / 2)) - i);
        } else if (horizontalRelativeTo == 9) {
            if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
                if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                    leafView.setX((pageAttr.pageWidth - (pageAttr.rightMargin / 2)) - i);
                } else {
                    leafView.setX((pageAttr.leftMargin / 2) - i);
                }
            }
        } else if (horizontalRelativeTo == 8 && leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
            if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                leafView.setX((pageAttr.leftMargin / 2) - i);
            } else {
                leafView.setX((pageAttr.pageWidth - (pageAttr.rightMargin / 2)) - i);
            }
        }
    }

    private void processHorizontalPosition_Right(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        Rectangle bounds = wPAutoShape.getBounds();
        byte horizontalRelativeTo = wPAutoShape.getHorizontalRelativeTo();
        if (horizontalRelativeTo == 2 || horizontalRelativeTo == 5) {
            leafView.setX(pageAttr.pageWidth - bounds.width);
        } else if (horizontalRelativeTo == 1 || horizontalRelativeTo == 0) {
            leafView.setX((pageAttr.pageWidth - pageAttr.rightMargin) - bounds.width);
        } else if (horizontalRelativeTo == 3 || horizontalRelativeTo == 4) {
            leafView.setX(pageAttr.leftMargin - bounds.width);
        } else if (horizontalRelativeTo == 9) {
            if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
                if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                    leafView.setX(pageAttr.pageWidth - bounds.width);
                } else {
                    leafView.setX(pageAttr.leftMargin - bounds.width);
                }
            }
        } else if (horizontalRelativeTo == 8 && leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
            if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                leafView.setX(pageAttr.leftMargin - bounds.width);
            } else {
                leafView.setX(pageAttr.pageWidth - bounds.width);
            }
        }
    }

    private void processHorizontalPosition_Inside(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
            Rectangle bounds = wPAutoShape.getBounds();
            byte horizontalRelativeTo = wPAutoShape.getHorizontalRelativeTo();
            if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                if (horizontalRelativeTo == 2) {
                    leafView.setX(0);
                } else if (horizontalRelativeTo == 1) {
                    leafView.setX(pageAttr.leftMargin);
                }
            } else if (horizontalRelativeTo == 2) {
                leafView.setX(pageAttr.pageWidth - bounds.width);
            } else if (horizontalRelativeTo == 1) {
                leafView.setX((pageAttr.pageWidth - pageAttr.rightMargin) - bounds.width);
            }
        }
    }

    private void processHorizontalPosition_Outside(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
            Rectangle bounds = wPAutoShape.getBounds();
            byte horizontalRelativeTo = wPAutoShape.getHorizontalRelativeTo();
            if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                if (horizontalRelativeTo == 2) {
                    leafView.setX(pageAttr.pageWidth - bounds.width);
                } else if (horizontalRelativeTo == 1) {
                    leafView.setX((pageAttr.pageWidth - pageAttr.rightMargin) - bounds.width);
                }
            } else if (horizontalRelativeTo == 2) {
                leafView.setX(0);
            } else if (horizontalRelativeTo == 1) {
                leafView.setX(pageAttr.leftMargin);
            }
        }
    }

    private void processVerticalPosition(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        byte verPositionType = wPAutoShape.getVerPositionType();
        byte verticalRelativeTo = wPAutoShape.getVerticalRelativeTo();
        if (verPositionType == 1) {
            float verRelativeValue = ((float) wPAutoShape.getVerRelativeValue()) / 1000.0f;
            if (verticalRelativeTo == 2) {
                leafView.setY(Math.round(((float) pageAttr.pageHeight) * verRelativeValue));
            } else if (verticalRelativeTo == 1) {
                leafView.setY(pageAttr.topMargin + Math.round(((float) ((pageAttr.pageHeight - pageAttr.topMargin) - pageAttr.bottomMargin)) * verRelativeValue));
            } else if (verticalRelativeTo == 6) {
                leafView.setY(Math.round(((float) pageAttr.topMargin) * verRelativeValue));
            } else if (verticalRelativeTo == 7) {
                leafView.setY((pageAttr.pageHeight - pageAttr.bottomMargin) + Math.round(((float) pageAttr.bottomMargin) * verRelativeValue));
            } else if ((verticalRelativeTo == 9 || verticalRelativeTo == 8) && leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
                if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                    leafView.setY(Math.round(((float) pageAttr.topMargin) * verRelativeValue));
                } else {
                    leafView.setY((pageAttr.pageHeight - pageAttr.bottomMargin) + Math.round(((float) pageAttr.bottomMargin) * verRelativeValue));
                }
            }
        } else {
            byte verticalAlignment = wPAutoShape.getVerticalAlignment();
            if (verticalAlignment == 0) {
                processVerticalPosition_Absolute(leafView, wPAutoShape, pageAttr);
            } else if (verticalAlignment == 4) {
                processVerticalPosition_Top(leafView, wPAutoShape, pageAttr);
            } else if (verticalAlignment == 2) {
                processVerticalPosition_Center(leafView, wPAutoShape, pageAttr);
            } else if (verticalAlignment == 5) {
                processVerticalPosition_Bottom(leafView, wPAutoShape, pageAttr);
            } else if (verticalAlignment == 6) {
                processVerticalPosition_Inside(leafView, wPAutoShape, pageAttr);
            } else if (verticalAlignment == 7) {
                processVerticalPosition_Outside(leafView, wPAutoShape, pageAttr);
            }
        }
    }

    private void processVerticalPosition_Absolute(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        Rectangle bounds = wPAutoShape.getBounds();
        byte verticalRelativeTo = wPAutoShape.getVerticalRelativeTo();
        if (verticalRelativeTo == 2 || verticalRelativeTo == 6) {
            leafView.setY(bounds.y);
        } else if (verticalRelativeTo == 8 || verticalRelativeTo == 9) {
            if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
                if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                    leafView.setY(bounds.y);
                } else {
                    leafView.setY((pageAttr.pageHeight - pageAttr.bottomMargin) + bounds.y);
                }
            }
        } else if (verticalRelativeTo == 1) {
            leafView.setY(pageAttr.topMargin + bounds.y);
        } else if (verticalRelativeTo == 10 || verticalRelativeTo == 11) {
            if (leafView.getParentView() != null && (leafView.getParentView().getParentView() instanceof ParagraphView)) {
                leafView.setY(((ParagraphView) leafView.getParentView().getParentView()).getY() + bounds.y);
            }
        } else if (verticalRelativeTo == 7) {
            leafView.setY((pageAttr.pageHeight - pageAttr.bottomMargin) + bounds.y);
        }
    }

    private void processVerticalPosition_Top(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        wPAutoShape.getBounds();
        byte verticalRelativeTo = wPAutoShape.getVerticalRelativeTo();
        if (verticalRelativeTo == 2 || verticalRelativeTo == 6) {
            leafView.setY(0);
        } else if (verticalRelativeTo == 8 || verticalRelativeTo == 9) {
            if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
                if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                    leafView.setY(0);
                } else {
                    leafView.setY(pageAttr.pageHeight - pageAttr.bottomMargin);
                }
            }
        } else if (verticalRelativeTo == 1) {
            leafView.setY(pageAttr.topMargin);
        } else if (verticalRelativeTo == 10 || verticalRelativeTo == 11) {
            if (leafView.getParentView() != null && (leafView.getParentView().getParentView() instanceof ParagraphView)) {
                leafView.setY(((ParagraphView) leafView.getParentView().getParentView()).getY());
            }
        } else if (verticalRelativeTo == 7) {
            leafView.setY(pageAttr.pageHeight - pageAttr.bottomMargin);
        }
    }

    private void processVerticalPosition_Center(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        Rectangle bounds = wPAutoShape.getBounds();
        byte verticalRelativeTo = wPAutoShape.getVerticalRelativeTo();
        int i = bounds.height / 2;
        if (verticalRelativeTo == 2) {
            leafView.setY((pageAttr.pageHeight / 2) - i);
        } else if (verticalRelativeTo == 1) {
            leafView.setY((pageAttr.topMargin + (((pageAttr.pageHeight - pageAttr.topMargin) - pageAttr.bottomMargin) / 2)) - i);
        } else if (verticalRelativeTo == 6) {
            leafView.setY((pageAttr.topMargin / 2) - i);
        } else if (verticalRelativeTo == 8 || verticalRelativeTo == 9) {
            if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
                if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                    leafView.setY((pageAttr.topMargin / 2) - i);
                } else {
                    leafView.setY((pageAttr.pageHeight - (pageAttr.bottomMargin / 2)) - i);
                }
            }
        } else if (verticalRelativeTo == 7) {
            leafView.setY((pageAttr.pageHeight - (pageAttr.bottomMargin / 2)) - i);
        } else if ((verticalRelativeTo == 10 || verticalRelativeTo == 11) && leafView.getParentView() != null && (leafView.getParentView().getParentView() instanceof ParagraphView)) {
            leafView.setY(((ParagraphView) leafView.getParentView().getParentView()).getY() - i);
        }
    }

    private void processVerticalPosition_Bottom(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        Rectangle bounds = wPAutoShape.getBounds();
        byte verticalRelativeTo = wPAutoShape.getVerticalRelativeTo();
        if (verticalRelativeTo == 2 || verticalRelativeTo == 7) {
            leafView.setY(pageAttr.pageHeight - bounds.height);
        } else if (verticalRelativeTo == 1) {
            leafView.setY((pageAttr.pageHeight - pageAttr.bottomMargin) - bounds.height);
        } else if (verticalRelativeTo == 10 || verticalRelativeTo == 11) {
            if (leafView.getParentView() != null && (leafView.getParentView().getParentView() instanceof ParagraphView)) {
                ParagraphView paragraphView = (ParagraphView) leafView.getParentView().getParentView();
                leafView.setY((paragraphView.getY() + paragraphView.getHeight()) - bounds.height);
            }
        } else if (verticalRelativeTo == 6) {
            leafView.setY(pageAttr.topMargin - bounds.height);
        } else if ((verticalRelativeTo == 8 || verticalRelativeTo == 9) && leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
            if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                leafView.setY(pageAttr.topMargin - bounds.height);
            } else {
                leafView.setY(pageAttr.pageHeight - bounds.height);
            }
        }
    }

    private void processVerticalPosition_Inside(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        Rectangle bounds = wPAutoShape.getBounds();
        byte verticalRelativeTo = wPAutoShape.getVerticalRelativeTo();
        if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
            if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                if (verticalRelativeTo == 2) {
                    leafView.setY(pageAttr.headerMargin / 2);
                } else if (verticalRelativeTo == 1) {
                    leafView.setY(pageAttr.topMargin);
                } else if (verticalRelativeTo == 10 || verticalRelativeTo == 11) {
                    leafView.setY(((ParagraphView) leafView.getParentView().getParentView()).getY());
                } else if (verticalRelativeTo == 6) {
                    leafView.setY(0);
                } else if (verticalRelativeTo == 7) {
                    leafView.setY(pageAttr.pageHeight - pageAttr.bottomMargin);
                } else if (verticalRelativeTo == 8 || verticalRelativeTo == 9) {
                    leafView.setY(0);
                }
            } else if (verticalRelativeTo == 2) {
                leafView.setY(pageAttr.pageHeight - pageAttr.footerMargin);
            } else if (verticalRelativeTo == 1) {
                leafView.setY((pageAttr.pageHeight - pageAttr.bottomMargin) - bounds.height);
            } else if (verticalRelativeTo == 10 || verticalRelativeTo == 11) {
                ParagraphView paragraphView = (ParagraphView) leafView.getParentView().getParentView();
                leafView.setY((paragraphView.getY() + paragraphView.getHeight()) - bounds.height);
            } else if (verticalRelativeTo == 6) {
                leafView.setY(pageAttr.topMargin - bounds.height);
            } else if (verticalRelativeTo == 7) {
                leafView.setY(pageAttr.pageHeight - bounds.height);
            } else if (verticalRelativeTo == 8 || verticalRelativeTo == 9) {
                leafView.setY(pageAttr.pageHeight - bounds.height);
            }
        }
    }

    private void processVerticalPosition_Outside(LeafView leafView, WPAutoShape wPAutoShape, PageAttr pageAttr) {
        Rectangle bounds = wPAutoShape.getBounds();
        byte verticalRelativeTo = wPAutoShape.getVerticalRelativeTo();
        if (leafView.getParentView() != null && leafView.getParentView().getParentView() != null && leafView.getParentView().getParentView().getParentView() != null) {
            if (((PageView) leafView.getParentView().getParentView().getParentView()).getPageNumber() % 2 == 1) {
                if (verticalRelativeTo == 2) {
                    leafView.setY(pageAttr.pageHeight - pageAttr.footerMargin);
                } else if (verticalRelativeTo == 1) {
                    leafView.setY((pageAttr.pageHeight - pageAttr.bottomMargin) - bounds.height);
                } else if (verticalRelativeTo == 10 || verticalRelativeTo == 11) {
                    ParagraphView paragraphView = (ParagraphView) leafView.getParentView().getParentView();
                    leafView.setY((paragraphView.getY() + paragraphView.getHeight()) - bounds.height);
                } else if (verticalRelativeTo == 6) {
                    leafView.setY(pageAttr.topMargin - bounds.height);
                } else if (verticalRelativeTo == 7) {
                    leafView.setY(pageAttr.pageHeight - bounds.height);
                } else if (verticalRelativeTo == 8 || verticalRelativeTo == 9) {
                    leafView.setY(pageAttr.topMargin - bounds.height);
                }
            } else if (verticalRelativeTo == 2) {
                leafView.setY(pageAttr.headerMargin / 2);
            } else if (verticalRelativeTo == 1) {
                leafView.setY(pageAttr.topMargin);
            } else if (verticalRelativeTo == 10 || verticalRelativeTo == 11) {
                leafView.setY(((ParagraphView) leafView.getParentView().getParentView()).getY());
            } else if (verticalRelativeTo == 6) {
                leafView.setY(0);
            } else if (verticalRelativeTo == 7) {
                leafView.setY(pageAttr.pageHeight - pageAttr.bottomMargin);
            } else if (verticalRelativeTo == 8 || verticalRelativeTo == 9) {
                leafView.setY(pageAttr.pageHeight - pageAttr.bottomMargin);
            }
        }
    }
}
