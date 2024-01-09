//React
import React, { useState, useEffect } from 'react'

//MUI
import { styled } from '@mui/material/styles'
import { grey } from '@mui/material/colors'
import Box from '@mui/material/Box'
import Drawer from '@mui/material/SwipeableDrawer'
import Skeleton from '@mui/material/Skeleton'
import DragHandleIcon from '@mui/icons-material/DragHandle'
import ListItemButton from '@mui/material/ListItemButton'
import ImageIcon from '@mui/icons-material/Image'
import { useTheme } from '@emotion/react'
import BusIcon from '@mui/icons-material/DirectionsBus'
import { GlobalStyles } from '@mui/material'
import { SearchBox } from './SearchBox'
import MenuList from './MenuList'
import MenuAccordion from './menuAccordion'

import { fetchAgencies } from '../api/api'

const routes = [
  {
    "title": "Purple Line",
    "tag": "fullservice",
    "color": "#800080",
    "directions": [
      {
        "title": "Inbound",
        "tag": "inbound",
      },
      {
        "title": "Outbound",
        "tag": "outbound",
      }
    ]
  },
  {
    "title": "Red Line",
    "tag": "express",
    "color": "#ff0000",
    "directions": [
      {
        "title": "Inbound",
        "tag": "inbound",
      },
      {
        "title": "Outbound",
        "tag": "outbound",
      }
    ]
  }
]

//style constants
const DRAWER_BLEEDING = 31
const ICON_WIDTH = '16px'
const DRAWER_HEIGHT = '75%'

const inputGlobalStyles = (
<GlobalStyles
  styles={{
    '.MuiDrawer-root > .MuiPaper-root': {
      height: `calc(${DRAWER_HEIGHT} - ${DRAWER_BLEEDING}px)`,
      overflow: 'visible',
    }
  }}
/>
)

const Menu = () => {
  const [open, setOpen] = React.useState(true)
  const [showAgencies, setShowAgencies] = useState(true)
  const [agencies, setAgencies] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAgencies().then((agencies) => {
      setAgencies(agencies);
      setLoading(false);
    });
  }, []);


  const toggleDrawer = (open) => {
    setOpen(open)
  }

  const handleAgencyClick = (agencyTag) => {
    console.log(agencyTag)
    setShowAgencies(false)
  }

  //prop constants
  const ANCHOR = 'bottom'

  const theme = useTheme()

  return (
    <>
      {inputGlobalStyles}
      <Drawer
        swipeAreaWidth={DRAWER_BLEEDING}
        anchor={ANCHOR}
        open={open}
        onClose={() => {toggleDrawer(false)}}
        onOpen={() => {toggleDrawer(true)}}
      >
        <Box
          sx={{
            border: 'none',
            backgroundColor: 'white',
            position: 'absolute',
            top: -DRAWER_BLEEDING,
            visibility: 'visible',
            borderRadius: `${theme.spacing(1)} ${theme.spacing(1)} 0 0`,
            right: 0,
            left: 0,
            elevation: 0,
          }}
        >
          <DragHandleIcon
            sx={{
            position: 'relative', left: `calc(50% - ${ICON_WIDTH} / 2)`,
            }}
          />
        </Box>
        <Box
          sx={{
            px: 2,
            py: 2,
            height: '100%',
            overflow: 'auto',
          }}
        >
          <SearchBox/>
          <Box
            sx={{
              borderRadius: 1,
              // border: `1px solid ${theme.palette.grey.A400}`,
              border: 'none',
              height: '100%',
              overflow: 'scroll',
            }}
          >
            {
              loading ?
                <>
                  {
                    [...Array(8)].map((item, index) => {
                      return (
                        <Skeleton
                          key={index}
                          variant="rectangular"
                          width="100%"
                          height={70}
                          sx={{
                            borderRadius: 1,
                            my: 1,
                          }}
                        />
                      )
                    })
                  }
                </>
                :
                <>
                  {
                    showAgencies ?
                    <>
                      <MenuList
                        list={agencies}
                        handleClick={handleAgencyClick}
                      />
                    </>
                    :
                    <>
                      <MenuAccordion
                        routes={routes}
                      />
                    </>
                  }
                </>
            }
          </Box>
        </Box>
      </Drawer>
    </>
  )
}

export default Menu